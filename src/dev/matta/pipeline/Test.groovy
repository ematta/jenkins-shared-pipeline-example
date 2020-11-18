package dev.matta.pipeline

import dev.matta.custom.Build
import dev.matta.custom.Information
import dev.matta.custom.SourceCode
import dev.matta.custom.TestExecutor
import dev.matta.enums.BuildType
import dev.matta.enums.TestType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.General
import dev.matta.utilities.SlackMessaging

class Test implements IPipeline{
    IStepExecutor steps
    @Override
    Map job(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.type = BuildType.TESTING
        map.environment = General.getEnv(map.env as String)
        steps.stage("Git pull", { SourceCode.pullTestingCode(map, { _test(map) }) })
        return map
    }

    @Override
    def reporting(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage("Test Reports", { Information.buildTestReport(map) })
    }

    @Override
    def notification(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.currentBuild = steps.currentBuild()
        map.message = SlackMessaging.testingMessage(map)
        steps.stage("Notify Slack", { Information.notifySlack(map) })
    }

    private def _test(Map map) {
        steps.stage("Test Service", { TestExecutor.runAll(map) })
    }
}

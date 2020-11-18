package dev.matta.pipeline

import dev.matta.custom.Build
import dev.matta.custom.Information
import dev.matta.custom.SourceCode
import dev.matta.enums.BuildType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.SlackMessaging

class SharedPipeline implements IPipeline {
    IStepExecutor steps

    @Override
    Map job(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.type = BuildType.PIPELINE
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage("Git pull", { SourceCode.pullServiceCode(map, { service(map) }) })
        return map
    }

    @Override
    def reporting(Map map) {
        steps.stage("Build Reports", { Information.buildGradleReport() })
    }

    private def service(Map map) {
        steps.stage("Gradle Build", { Build.gradle() })
    }

    @Override
    def notification(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.currentBuild = steps.currentBuild()
        map.message = SlackMessaging.serviceMessage(map)
        steps.stage("Notify Slack", { Information.notifySlack(map) })
    }
}

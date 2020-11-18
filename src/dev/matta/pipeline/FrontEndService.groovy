package dev.matta.pipeline

import dev.matta.custom.*
import dev.matta.enums.BuildType
import dev.matta.enums.TestType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.General
import dev.matta.utilities.SlackMessaging

class FrontEndService implements IPipeline {
    IStepExecutor steps

    @Override
    Map job(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.type = BuildType.FRONTEND
        map.environment = General.getEnv(map.env as String)
        map.testType = TestType.FRONTEND
        steps.stage("Git pull", { SourceCode.pullServiceCode(map, { service(map) }) })
        steps.stage("Calling External Build", { External.executeExternalBuildForDeployment(map) })
        steps.stage("Calling External Test", { External.executeExternalBuildForTest(map) })
        return map
    }

    @Override
    def reporting(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage("Node JS report", { Information.buildNodeJsReport() })
    }

    @Override
    def notification(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.currentBuild = steps.currentBuild()
        map.message = SlackMessaging.serviceMessage(map)
        steps.stage("Notify Slack", { Information.notifySlack(map) })
    }

    private def service(Map map) {
        steps.stage("Build Frontend", { Build.nodejs() })
        map.version = Version.bumpedVersion(map).toString()
        map.runManifest = map.version as String != 'latest'
        map.component = 'console'
        if (map.runManifest as Boolean) {
            steps.stage("Build Frontend", { SourceCode.tag(map) })
        }
        steps.stage("Push Docker", { DockerMaintainer.deploy(map) })
    }
}

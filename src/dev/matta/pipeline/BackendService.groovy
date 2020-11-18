package dev.matta.pipeline

import dev.matta.custom.*
import dev.matta.enums.BuildType
import dev.matta.enums.TestType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.General
import dev.matta.utilities.SlackMessaging

class BackendService implements IPipeline {
    IStepExecutor steps

    @Override
    Map job(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.type = BuildType.BACKEND
        map.environment = General.getEnv(map.env as String)
        map.testType = TestType.BACKEND
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage("Git pull", { SourceCode.pullServiceCode(map, { service(map) }) })
        steps.stage("Calling External Build", { External.executeExternalBuildForDeployment(map) })
        steps.stage("Calling External Test", { External.executeExternalBuildForTest(map) })
        return map
    }

    private def service(Map map) {
        steps.stage("Gradle Build", { Build.gradle() })
        map.version = Version.bumpedVersion(map).toString()
        map.runManifest = true
        map.component = (map.service as String)
        steps.stage("Tag Build", { SourceCode.tag(map) })
        steps.stage("Push Docker", { DockerMaintainer.deploy(map) })
    }

    @Override
    def reporting(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage("Build Reports", { Information.buildGradleReport() })
    }

    @Override
    def notification(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.currentBuild = steps.currentBuild()
        map.message = SlackMessaging.serviceMessage(map)
        steps.stage("Notify Slack", { Information.notifySlack(map) })
    }
}

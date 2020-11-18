package dev.matta.pipeline

import dev.matta.custom.Build
import dev.matta.custom.Deployment
import dev.matta.custom.Information
import dev.matta.custom.SourceCode
import dev.matta.enums.BuildType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.General
import dev.matta.utilities.SlackMessaging

class Deploy implements IPipeline {
    IStepExecutor steps
    @Override
    Map job(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        map.type = BuildType.DEPLOYMENT
        map.environment = General.getEnv(map.env as String)
        steps.stage("Deploy Service", { deployment(map) })
        return map
    }

    private def deployment(Map map) {
        if (map.get('runManifest', false)) {
            steps.stage("Update Manifest", { SourceCode.manifest(map) })
        }
        steps.stage("Deploy to environment", { Deployment.deploy(map) })
    }

    @Override
    def reporting(Map map) {
        steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage("Skipping Reporting", { steps.echo "Skipping" })
    }

    @Override
    def notification(Map map){
        steps = ContextRegistry.getContext().getStepExecutor()
        map.currentBuild = steps.currentBuild()
        map.message = SlackMessaging.deploymentMessage(map)
        steps.stage("Notify Slack", { Information.notifySlack(map) })
    }
}

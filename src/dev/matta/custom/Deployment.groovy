package dev.matta.custom

import dev.matta.enums.Environment
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.Constants
import dev.matta.utilities.General
import dev.matta.utilities.HostingInfo

class Deployment implements Serializable {
    static def deploy(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        switch (map.environment as Environment) {
            case Environment.DEV:
                map.command = General.devDeployCmd(map.component as String)
                break
            case Environment.QA:
                map.command = General.qaDeployCmd(map.component as String)
                break
            default:
                break
        }
        steps.withCredentials(bindings: [
            steps.sshUserPrivateKey(
                credentialsId: HostingInfo.credentialsId(map.environment as Environment),
                keyFileVariable: 'KEYFILE'
            )
        ]) {
            def env = steps.env()
            map.keyfile = env.KEYFILE
            _runDeploy(map)
        }
    }

    static def _runDeploy(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.sshCommand(
            remote: HostingInfo.info(map),
            command: map.command,
            failOnError: true
        )
    }
}

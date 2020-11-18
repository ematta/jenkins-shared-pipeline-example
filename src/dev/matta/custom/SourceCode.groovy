package dev.matta.custom

import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.Constants
import dev.matta.utilities.General

class SourceCode implements Serializable {
    static def pullTestingCode(Map map, Closure closure) {
        map.url = General.gitTestRepo(map)
        map.branch = General.gitTestBranch(map)
        map.credentialsId = Constants.GITHUB_CREDENTIALS_ID
        pullSource(map, closure)
    }

    static def pullServiceCode(Map map, Closure closure) {
        map.url = General.gitUrl(map.service as String)
        map.credentialsId = Constants.GITHUB_CREDENTIALS_ID
        pullSource(map, closure)
    }

    static private def pullSource(Map map, Closure closure) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.cleanWs()
        steps.git(
            url: map.url as String,
            branch: map.branch as String,
            credentialsId: map.credentialsId as String
        )
        closure()
    }

    static def tag(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.withCredentials(bindings: [
            steps.usernamePassword(
                credentialsId: Constants.GITHUB_CREDENTIALS_ID,
                passwordVariable: 'GIT_PASSWORD',
                usernameVariable: 'GIT_USERNAME',
            )
        ]) {
            def env = steps.env()
            map.username = env.GIT_USERNAME
            map.password = env.GIT_PASSWORD
            gitCmdForTag(map)
        }
    }

    static def gitCmdForTag(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.sh(script: "git tag -a ${map.version as String} -m 'version ${map.version as String}'")
        steps.sh(script: "git push https://${map.username}:${map.password}@github.com/ematta/${map.service as String}/ --tags")
    }

    static def manifest(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.stage('Update Manifest') {
            pullServiceCode(
                service: 'server-config',
                branch: 'ci'
            ) {
                steps.withCredentials(bindings: [
                    steps.usernamePassword(
                        credentialsId: Constants.GITHUB_CREDENTIALS_ID,
                        passwordVariable: 'GIT_PASSWORD',
                        usernameVariable: 'GIT_USERNAME',
                    )
                ]) {
                    def env = steps.env()
                    if (env) {
                        map.username = env.GIT_USERNAME
                        map.password = env.GIT_PASSWORD
                    }
                    gitCmdForManifest(map)
                }
            }
        }
    }
}

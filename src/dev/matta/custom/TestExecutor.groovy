package dev.matta.custom

import dev.matta.enums.TestType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.General

class TestExecutor implements Serializable {

    static def runAll(Map map) {
        switch (map.testType as TestType) {
            case TestType.BACKEND:
                map.testingId = General.testingCredentialsIdForBackend(map)
                break
            case TestType.FRONTEND:
                map.testingId = General.testingCredentialsIdForFrontend(map)
                break
            default:
                break
        }
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.echo "Testing ID ${map.testingId}"
        steps.withCredentials(bindings: [
            steps.file(credentialsId: map.testingId, variable: map.testingId)
        ]) {
            def cmd = _shellCmd(map)
            steps.nodejs('node') {
                steps.sh(script: cmd)
            }
        }
    }

    static def _shellCmd(Map map) {
        switch (map.testType as TestType) {
            case TestType.BACKEND:
                return "CONFIG=\$${map.testingId as String} make ci"
            case TestType.FRONTEND:
                return "CI=true NODE_ENV=test npx yarn install && CI=true NODE_ENV=test npx yarn cy run --config-file ${map.testingId as String} --headless --record --key '' --browser chrome"
            default:
                return ''
                break
        }
    }
}

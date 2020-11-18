package dev.matta.custom


import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor

class Build implements Serializable {
    /**
     * Executes a build with either Gradle or NPM (more to come)
     *
     * Config is a map and it needs to have:
     *  service: The service we are building
     * @param map
     * @return
     */
    static def gradle() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.sh script: './gradlew clean build'
    }

    static def nodejs() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.nodejs('node') {
            steps.sh script: 'CI=true NODE_ENV=dev npx yarn install && npx yarn build && npx yarn test --coverage --watchAll=false'
        }
    }

    /**
     * Published jar using Gradle Publish
     * @return
     */
    static def jar() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.sh(script: './gradlew publish')
    }

    /**
     * Cleans our workspace
     * @return
     */
    static def cleanWs() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.cleanWs()
    }
}

package dev.matta.custom

import dev.matta.enums.BuildType
import dev.matta.exception.PipelineException
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.Constants

class DockerMaintainer implements Serializable {
    static def dockerRegistry(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.withDockerRegistry(
            credentialsId: Constants.DOCKER_CREDENTIALS_ID,
            url: Constants.DOCKER_URL
        ) {
            pushDocker(map)
        }
    }

    static def deploy(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.script {
            dockerRegistry(map)
        }
    }

    static def pushDocker(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        switch (map.type as BuildType) {
            case BuildType.BACKEND:
                steps.writeFile file: "gradle.properties", text: "version=${map.version as String}"
                steps.sh script: "./gradlew build dockerClean dockerTag${map.version as String} dockerPush"
                break
            case BuildType.FRONTEND:
                def docker = steps.docker()
                def image = docker.build("${Constants.DOCKER_ECR}/console:${map.version as String}")
                image.push()
                break
            default:
                throw new PipelineException("Docker Services does not handle type")
        }
    }
}

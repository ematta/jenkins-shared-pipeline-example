package dev.matta.custom

import dev.matta.BaseTest
import dev.matta.enums.BuildType
import org.junit.jupiter.api.Test

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.verify

class DockerMaintainerTest extends BaseTest {

    @Test
    void pushDockerBackend() {
        DockerMaintainer dockerMaintainer = new DockerMaintainer()
        Map map = [:]
        map.type = BuildType.BACKEND
        map.version = "0.0.1"
        dockerMaintainer.pushDocker(map)
        verify(_steps).writeFile(file: "gradle.properties", text: "version=${map.version as String}")
        verify(_steps).sh(script: "./gradlew build dockerClean dockerTag${map.version as String} dockerPush")
    }

    @Test
    void dockerRegistry() {
        DockerMaintainer dockerMaintainer = new DockerMaintainer()
        dockerMaintainer.dockerRegistry(type:  BuildType.BACKEND, version: "0.0.1")
        verify(_steps).withDockerRegistry(any(), any())
    }
}

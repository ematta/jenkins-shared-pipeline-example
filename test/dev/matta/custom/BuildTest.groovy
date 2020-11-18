package dev.matta.custom

import dev.matta.BaseTest
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.verify

class BuildTest extends BaseTest {

    @Test
    void gradle() {
        Build.gradle()
        verify(_steps).sh script: './gradlew clean build'
    }

    @Test
    void jar() {
        Build.jar()
        verify(_steps).sh(script: './gradlew publish')
    }

    @Test
    void cleanWs() {
        Build.cleanWs()
        verify(_steps).cleanWs()
    }
}

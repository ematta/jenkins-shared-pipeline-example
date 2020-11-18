package dev.matta.custom

import dev.matta.BaseTest
import dev.matta.utilities.Constants
import dev.matta.utilities.SemVer
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.verify

class VersionTest extends BaseTest {

    @Test
    void gradle() {
        def expected = new SemVer(0,0,0)
        def ver = "version=${expected}"
        def actual = Version.gradle([:])
        verify(_steps).sh(script: Constants.VERSION, returnStdout: true)
        verify(_steps).readFile('gradle.properties')
        verify(_steps).writeFile file: "gradle.properties", text: ver
        assertEquals(actual as String, expected as String)
    }
    @Test
    void _nodeCommands() {
        def expected = new SemVer(0,0,0)
        def actual = Version._nodeCommands([:])
        verify(_steps).sh(script: Constants.VERSION, returnStdout: true)
        verify(_steps).sh(script: Constants.NODE_VERSION_SCRIPT, returnStdout: true)
        assertEquals(actual as String, expected as String)
    }
}

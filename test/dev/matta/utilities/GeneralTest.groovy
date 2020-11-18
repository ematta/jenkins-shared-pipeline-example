package dev.matta.utilities

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GeneralTest extends GroovyTestCase {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void nodeChoice() {
        def nodes = ['worker', 'worker2']
        def chosen
        chosen = General.nodeChoice()
        assertTrue(chosen in nodes)
    }

    @Test
    void gitUrl() {
        String expected = "https://github.com/ematta//"
        String actual
        actual = General.gitUrl('')
        assertEquals(expected, actual)
    }

    @Test
    void testDeploymentCommandBackend() {
        String testCmd = ""
        String comparedCmd
        comparedCmd = General.devDeployCmd("")
        assertEquals(comparedCmd.trim(), testCmd.trim())
    }

    @Test
    void testDeploymentCommandFrontend() {
        String testCmd = ""
        String comparedCmd
        comparedCmd = General.devDeployCmd("")
        assertEquals(comparedCmd.trim(), testCmd.trim())
    }
}

package dev.matta.utilities

import dev.matta.enums.Environment
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SlackMessagingTest extends GroovyTestCase {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /*@Test
    void testServiceMessage() {
        Map map = [:]
        map.version = "0.0.1"
        map.service = ""
        Map currentBuild = [:]
        currentBuild.projectName = ""
        currentBuild.currentResult = "SUCCESS"
        currentBuild.absoluteUrl = ""
        map.currentBuild = currentBuild
        def expected = ""
        def actual = SlackMessaging.serviceMessage(map)
        assertEquals(actual.trim(), expected.trim())
    }*/
}

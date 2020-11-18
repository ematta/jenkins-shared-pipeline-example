package dev.matta.custom

import dev.matta.BaseTest
import dev.matta.enums.Environment
import dev.matta.enums.TestType
import dev.matta.utilities.General
import org.junit.jupiter.api.Test

class TestExecutorTest extends BaseTest {

    @Test
    void shellRunBackend() {
        Map map = [:]
        map.testType = TestType.BACKEND
        map.environment = Environment.QA
        map.testingId = General.testingCredentialsIdForBackend(map)
        def actual = TestExecutor._shellCmd(map)
        def expected = "CONFIG=\$${map.testingId as String} make ci"
        assertEquals(expected, actual)
    }

    /*@Test
    void shellRunFrontend() {
        Map map = [:]
        map.testType = TestType.FRONTEND
        map.environment = Environment.QA
        map.testingId = General.testingCredentialsIdForFrontend(map)
        def expected = "CI=true NODE_ENV=test npx yarn install && CI=true NODE_ENV=test npx yarn cy run --config-file ${map.testingId as String} --headless --record --key '3ddcd292-9a2c-4be6-a217-d3777f5a5030' --browser chrome"
        def actual = TestExecutor._shellCmd(map)
        assertEquals(expected, actual)
    }*/
}

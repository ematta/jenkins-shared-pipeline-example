package dev.matta.custom

import dev.matta.BaseTest
import dev.matta.enums.TestType
import dev.matta.utilities.Constants
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.verify

class InformationTest extends BaseTest {
    @Test
    void buildGradleReport() {
        Information.buildGradleReport()
        verify(_steps).step([
            $class          : 'JacocoPublisher',
            execPattern     : 'build/jacoco/*.exec',
            classPattern    : 'build/classes',
            sourcePattern   : 'src/main/java',
            exclusionPattern: 'src/test*',
        ])
        verify(_steps).junit('build/test-results/test/**/*.xml')
        verify(_steps).sh(script: "./gradlew sonarqube")
        verify(_steps).archiveArtifacts(artifacts: 'build/jacocoHtml/**/*')
        verify(_steps).archiveArtifacts(artifacts: 'build/reports/**/*')
    }

    @Test
    void buildNodeJsReport() {
        Information.buildNodeJsReport()
        verify(_steps).step([
            $class              : 'CloverPublisher',
            cloverReportDir     : 'coverage',
            cloverReportFileName: 'clover.xml'
        ])
    }

    @Test
    void buildTestReportBackend() {
        Information.buildTestReport(testType: TestType.BACKEND)
        verify(_steps).archiveArtifacts(artifacts: 'data/**/*')
        verify(_steps).junit('build.xml')
    }

    @Test
    void notifySlack() {
        Information.notifySlack(message: 'foo')
        verify(_steps).slackSend(
            color: "warning",
            iconEmoji: "lightning",
            channel: "builds",
            message: "foo",
            tokenCredentialId: Constants.SLACK_TOKEN
        )
    }
}

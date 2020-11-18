package dev.matta.custom

import dev.matta.enums.TestType
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.Constants

class Information implements Serializable {
    static def buildGradleReport() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.step([
            $class          : 'JacocoPublisher',
            execPattern     : 'build/jacoco/*.exec',
            classPattern    : 'build/classes',
            sourcePattern   : 'src/main/java',
            exclusionPattern: 'src/test*',
        ])
        steps.junit('build/test-results/test/**/*.xml')
        steps.sh(script: "./gradlew sonarqube")
        steps.archiveArtifacts(artifacts: 'build/jacocoHtml/**/*')
        steps.archiveArtifacts(artifacts: 'build/reports/**/*')
    }

    static def buildNodeJsReport() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.step([
            $class              : 'CloverPublisher',
            cloverReportDir     : 'coverage',
            cloverReportFileName: 'clover.xml'
        ])
    }

    static def buildTestReport(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        switch (map.testType as TestType) {
            case TestType.BACKEND:
                steps.archiveArtifacts(artifacts: 'data/**/*')
                steps.junit('build.xml')
                break
            default:
                break
        }
    }

    static def notifySlack(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        def iconEmoji
        def color
        if (map.get('currentBuild')) {
            def currentResult = map.currentBuild
            iconEmoji = Constants.EMOJI[currentResult as String]
            color = Constants.COLOR_MAP[currentResult as String]
        } else {
            iconEmoji = Constants.EMOJI['UNSTABLE']
            color = Constants.COLOR_MAP['UNSTABLE']
        }
        steps.slackSend(
            color: color,
            iconEmoji: iconEmoji,
            channel: 'builds',
            message: map.message,
            tokenCredentialId: Constants.SLACK_TOKEN
        )
    }
}

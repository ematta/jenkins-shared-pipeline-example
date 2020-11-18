package dev.matta.custom

import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor

class External {
    static def executeExternalBuildForDeployment(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.build(
            job: 'auto-deployment',
            parameters: [
                steps.string(
                    name: 'component',
                    value: (map.service as String)
                ),
                steps.string(
                    name: 'version',
                    value: map.version as String
                ),
                steps.string(
                    name: "env",
                    value: map.env as String
                ),
                steps.string(
                    name: "buildType",
                    value: "DEPLOYMENT"
                )
            ],
            propagate: false,
            quietPeriod: 0,
            wait: false
        )
    }

    static def executeExternalBuildForTest(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.build(
            job: 'auto-testing',
            parameters: [
                steps.string(
                    name: 'service',
                    value: map.service as String
                ),
                steps.string(
                    name: 'version',
                    value: map.version as String
                ),
                steps.string(
                    name: "env",
                    value: map.env as String
                ),
                steps.string(
                    name: "buildType",
                    value: "TESTING"
                ),
                steps.string(
                    name: "testType",
                    value: map.testType as String
                )
            ],
            propagate: false,
            quietPeriod: 60,
            wait: false
        )
    }
}

import dev.matta.ConsolidatedBuild
import dev.matta.pipeline.IPipeline
import dev.matta.steps.ContextRegistry
import dev.matta.utilities.General

/**
 * Consolidated dev.matta.pipeline for everything but deployments
 * @param map Passed in from job
 *              example: ConsolidatedBuild(service: '', branch: 'master', environment: 'qa')
 */
void call(Map map = [:]) {
    if (map.isEmpty()) {
        map.putAll(params as Map)
    }
    ContextRegistry.registerDefaultContext(this)
    IPipeline builder = ConsolidatedBuild.buildChooser(map)
    pipeline {
        agent { label General.nodeChoice() }
        stages {
            stage("Building") {
                steps {
                    script {
                        map = builder.job(map) as Map
                    }
                }
            }
        }
        post {
            success {
                script {
                    builder.reporting(map)
                }
            }
            always {
                script {
                    builder.notification(map)
                }
            }
        }
    }
}

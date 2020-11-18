package dev.matta

import dev.matta.enums.BuildType
import dev.matta.exception.PipelineException
import dev.matta.pipeline.*
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.General

class ConsolidatedBuild {

    static IPipeline buildChooser(Map map) {
        switch (map.buildType.toString().toUpperCase() as BuildType) {
            case BuildType.BACKEND:
                return new BackendService()
            case BuildType.FRONTEND:
                return new FrontEndService()
            case BuildType.TESTING:
                return new Test()
            case BuildType.DEPLOYMENT:
                return new Deploy()
            case BuildType.PIPELINE:
                return new SharedPipeline()
            default:
                throw new PipelineException("${map.buildType} not implemented")
        }
    }
}

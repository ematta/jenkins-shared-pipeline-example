package dev.matta.custom

import dev.matta.enums.BuildType
import dev.matta.enums.Environment
import dev.matta.enums.PatchLevel
import dev.matta.exception.PipelineException
import dev.matta.steps.ContextRegistry
import dev.matta.steps.IStepExecutor
import dev.matta.utilities.Constants
import dev.matta.utilities.SemVer

class Version implements Serializable {
    private static def gradleFileVer() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        try {
            def fromFile = steps.readFile('gradle.properties').replaceAll("version=", '').trim()
            return new SemVer(fromFile as String)
        } catch (e) {
            steps.echo("PIPELINEERROR: Could not file ver for gradle, default to 0.0.0 ${e}")
            return new SemVer(0, 0, 0)
        }
    }

    private static def nodeFileVer() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        try {
            def fromFile = steps.sh(script: Constants.NODE_VERSION_SCRIPT, returnStdout: true).trim()
            return new SemVer(fromFile as String)
        } catch (e) {
            steps.echo("PIPELINEERROR: Could not file ver for node, default to 0.0.0 ${e}")
            return new SemVer(0, 0, 0)
        }
    }

    private static def gitVer() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        try {
            def fromGit = steps.sh(script: Constants.VERSION, returnStdout: true)
            return new SemVer(fromGit as String)
        } catch (e) {
            steps.echo("PIPELINEERROR: Could not git ver from git, default to 0.0.0 ${e}")
            return new SemVer(0, 0, 0)
        }
    }

    private static def writeVer(SemVer latestVersionFinal) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        try {
            def versionText = "version=${latestVersionFinal.toString()}"
            steps.writeFile file: "gradle.properties", text: versionText
        } catch (e) {
            steps.echo("PIPELINEERROR: Could not write file ${e}")
        }
    }

    static def gradle(Map map) {
        def git = gitVer()
        def file = gradleFileVer()
        def compared = (git.compare(file) > 0) ? git : file
        def latestVersionFinal = map.get('bump') ? compared.bump(PatchLevel.PATCH) : compared
        writeVer(latestVersionFinal)
        return latestVersionFinal
    }

    static def node(Map map) {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()
        steps.nodejs('node') {
            return _nodeCommands(map)
        }
    }

    static def _nodeCommands(Map map) {
        def git = gitVer()
        def file = nodeFileVer()
        def compared = (git.compare(file) > 0) ? git : file
        def latestVersionFinal = map.get('bump') ? compared.bump(PatchLevel.PATCH) : compared
        return latestVersionFinal
    }

    static def bumpedVersion(Map map) {
        switch (map.type as BuildType) {
            case BuildType.BACKEND:
                return gradle(bump: true)
            case BuildType.FRONTEND:
                if (map.environment as Environment == Environment.DEV) {
                    return 'latest'
                }
                return node(bump: true)
            default:
                throw new PipelineException("Build type not set for Versioning")
        }
    }
}

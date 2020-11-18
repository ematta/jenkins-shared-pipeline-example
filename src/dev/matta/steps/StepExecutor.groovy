package dev.matta.steps

class StepExecutor implements IStepExecutor {
    private _steps

    StepExecutor(steps) {
        this._steps = steps
    }

    @Override
    def currentBuild() {
        this._steps.currentBuild
    }

    @Override
    def env() {
        this._steps.env
    }

    @Override
    def docker() {
        this._steps.docker
    }

    @Override
    def sh(Map map) {
        this._steps.sh(map)
    }

    @Override
    def stage(String label, Closure body) {
        this._steps.stage(label, body)
    }

    @Override
    def nodejs(String label, Closure body) {
        this._steps.nodejs(label, body)
    }

    @Override
    def build(Map map) {
        this._steps.build(map)
    }

    @Override
    def string(Map map) {
        this._steps.string(map)
    }

    @Override
    def booleanParam(Map map) {
        this._steps.booleanParam(map)
    }

    @Override
    def sshCommand(Map map) {
        this._steps.sshCommand(map)
    }

    @Override
    def git(Map map) {
        this._steps.git(map)
    }

    @Override
    def slackSend(Map map) {
        this._steps.slackSend(map)
    }


    @Override
    def contentReplace(Map map) {
        this._steps.contentReplace(map)
    }

    @Override
    def fileContentReplaceConfig(Map map) {
        this._steps.fileContentReplaceConfig(map)
    }

    @Override
    def fileContentReplaceItemConfig(Map map) {
        this._steps.fileContentReplaceItemConfig(map)
    }

    @Override
    def junit(String location) {
        this._steps.junit(location)
    }

    @Override
    def step(Map map) {
        this._steps.step(map)
    }

    @Override
    def publishHTML(Map map) {
        this._steps.publishHTML(map)
    }

    @Override
    def echo(String message) {
        this._steps.echo(message)
    }

    @Override
    def readFile(String location) {
        this._steps.readFile(location)
    }

    @Override
    def writeFile(Map map) {
        this._steps.writeFile(map)
    }

    @Override
    def script(Closure closure) {
        this._steps.script(closure)
    }

    @Override
    def withDockerRegistry(Map map, Closure closure) {
        this._steps.withDockerRegistry(map, closure)
    }

    @Override
    def cleanWs() {
        this._steps.cleanWs()
    }

    @Override
    def withCredentials(Map map, Closure closure) {
        this._steps.withCredentials(map, closure)
    }

    @Override
    def usernamePassword(Map map) {
        this._steps.usernamePassword(map)
    }

    @Override
    def sshUserPrivateKey(Map map) {
        this._steps.sshUserPrivateKey(map)
    }

    @Override
    def archiveArtifacts(Map map) {
        this._steps.archiveArtifacts(map)
    }

    @Override
    def file(Map map) {
        this._steps.file(map)
    }
}

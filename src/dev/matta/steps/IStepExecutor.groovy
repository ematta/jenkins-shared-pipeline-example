package dev.matta.steps

interface IStepExecutor {
    def env()

    def docker()

    def sh(Map map)

    def stage(String label, Closure body)

    def nodejs(String label, Closure body)

    def build(Map map)

    def string(Map map)

    def booleanParam(Map map)

    def sshCommand(Map map)

    def git(Map map)

    def slackSend(Map map)

    def contentReplace(Map map)

    def fileContentReplaceConfig(Map map)

    def fileContentReplaceItemConfig(Map map)

    def junit(String location)

    def step(Map map)

    def publishHTML(Map map)

    def echo(String message)

    def readFile(String location)

    def writeFile(Map map)

    def script(Closure closure)

    def withDockerRegistry(Map map, Closure closure)

    def cleanWs()

    def withCredentials(Map map, Closure closure)

    def usernamePassword(Map map)

    def sshUserPrivateKey(Map map)

    def archiveArtifacts(Map map)

    def file(Map map)

    def currentBuild()
}

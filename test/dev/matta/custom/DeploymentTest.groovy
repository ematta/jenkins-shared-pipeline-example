package dev.matta.custom

import dev.matta.BaseTest
import dev.matta.enums.Environment
import dev.matta.utilities.General
import dev.matta.utilities.HostingInfo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.verify

class DeploymentTest extends BaseTest {

    private Map map = [
        environment: Environment.QA,
        component: 'console',
        version: '0.0.1'
    ]

    @AfterEach
    void tearDown() {
    }

    @Test
    void deploy() {
        Deployment deployment = new Deployment()
        deployment.deploy(map)
        verify(_steps).sshUserPrivateKey(
            ["credentialsId": "", "keyFileVariable": "KEYFILE"]
        )
    }

    @Test
    void runDeploy() {
        map.keyfile = "FOO"
        map.command = General.qaDeployCmd(map.component as String)
        Deployment._runDeploy(map)
        verify(_steps).sshCommand(
            remote: HostingInfo.info(map),
            command: map.command,
            failOnError: true
        )
    }
}

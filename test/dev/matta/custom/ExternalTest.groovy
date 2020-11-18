package dev.matta.custom

import dev.matta.BaseTest
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.verify

class ExternalTest extends BaseTest {

    /*@Test
    void executeExternalBuildForDeployment() {
        External.executeExternalBuildForDeployment(
            service: '',
            typeOverride: '',
            version: '',
            env: ''
        )
        verify(_steps).string(
            ["name": "component", "value": ""]
        )
        verify(_steps).string(
            ["name": "version", "value": ""]
        )
        verify(_steps).string(
            ["name": "env", "value": ""]
        )
        verify(_steps).string(
            ["name": "buildType", "value": ""]
        )
        verify(_steps).build(
            "job": "auto-deployment",
            "parameters": [null, null, null, null, null],
            propagate: false,
            quietPeriod: 0,
            wait: false
        )
    }*/

    /*@Test
    void executeExternalBuildForTest() {
        External.executeExternalBuildForTest(
            service: '',
            typeOverride: '',
            version: '',
            testType: '',
            env: ''
        )
        verify(_steps).string(
            ["name": "service", "value": ""]
        )
        verify(_steps).string(
            ["name": "buildType", "value": ""]
        )
        verify(_steps).string(
            ["name": "version", "value": ""]
        )
        verify(_steps).string(
            ["name": "env", "value": ""]
        )
        verify(_steps).string(
            ["name": "testType", "value": '']
        )
        verify(_steps).build(
            "job": "auto-testing",
            "parameters": [null, null, null, null, null],
            propagate: false,
            quietPeriod: 60,
            wait: false
        )
    }*/
}

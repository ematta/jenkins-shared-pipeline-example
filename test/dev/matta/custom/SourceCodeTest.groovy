package dev.matta.custom

import dev.matta.BaseTest;
import dev.matta.enums.TestType
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.verify

class SourceCodeTest extends BaseTest {

    /*@Test
    void pullTestingCodeBackend() {
        SourceCode.pullTestingCode(
            testType: TestType.BACKEND,
            branch: ""
        ) {}
        verify(_steps).git(
            url: "",
            branch: "",
            credentialsId: ""
        )
    }*/

    /*@Test
    void pullTestingCodeFrontend() {
        SourceCode.pullTestingCode(
            testType: TestType.FRONTEND,
            branch: ""
        ) {}
        verify(_steps).git(
            url: "",
            branch: "",
            credentialsId: ""
        )
    }*/

    /*@Test
    void pullServiceCode() {
        SourceCode.pullServiceCode(
            service: "",
            branch: ""
        ) {}
        verify(_steps).git(
            url: "",
            branch: "",
            credentialsId: ""
        )
    }*/

    @Test
    void gitCmdForTag() {
        Map map = [:]
        map.version = "1.0.0"
        map.service = ""
        SourceCode.gitCmdForTag(map)
        verify(_steps).sh(script: "git tag -a ${map.version as String} -m 'version ${map.version as String}'")
        verify(_steps).sh(script: "git push https://${map.username}:${map.password}@github.com/ematta/${map.service as String}/ --tags")
    }
}

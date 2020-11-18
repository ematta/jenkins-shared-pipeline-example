package dev.matta.utilities

import dev.matta.enums.BuildType
import dev.matta.enums.Environment
import dev.matta.enums.TestType
import dev.matta.exception.PipelineException

class General {
    static String nodeChoice() {
        List<String> nodes = ['worker', 'worker2']
        int nodesSize = nodes.size()
        Random rand = new Random()
        return nodes.get(rand.nextInt(nodesSize))
    }

    static def testingCredentialsIdForBackend(Map map) {
        switch (map.environment as Environment) {
            case Environment.DEV:
                return ''
            case Environment.QA:
                return ''
            default:
                break
        }
    }

    static def testingCredentialsIdForFrontend(Map map) {
        switch (map.environment as Environment) {
            case Environment.DEV:
                return ''
            case Environment.QA:
                return ''
            default:
                break
        }
    }

    static def gitUrl(String service) {
        return "https://github.com/ematta/${service}/"
    }

    static String devDeployCmd(String component) {
        String command
        if (component == 'console') {
            command = ""
        } else {
            command = ""
        }
        return command
    }

    static String qaDeployCmd(String component) {
        def command = ""
        return command
    }

    static def gitTestRepo(Map map) {
        def repo
        switch (map.testType as TestType) {
            case TestType.BACKEND:
                repo = ''
                break
            case TestType.FRONTEND:
                repo = ''
                break
            default:
                throw new PipelineException("Did not create ${map.testType as String} test type yet")
        }
        return "https://github.com/ematta/${repo}/"
    }

    static def gitTestBranch(Map map) {
        def branch
        switch (map.testType as TestType) {
            case TestType.BACKEND:
                branch = 'master'
                break
            case TestType.FRONTEND:
                branch = 'dev'
                break
            default:
                throw new PipelineException("Did not create ${map.testType as String} test type yet")
        }
        return branch
    }

    static BuildType getType(String service) {
        if (service == '') {
            return BuildType.FRONTEND
        } else {
            throw new PipelineException("Tried to find type of service, got ${service}")
        }
    }

    static Environment getEnv(String env) {
        switch (env) {
            case 'dev':
                return Environment.DEV
            case 'qa':
                return Environment.QA
            case 'staging':
                return Environment.STAGING
            case 'production':
                return Environment.PRODUCTION
            default:
                throw new PipelineException("Could not find ${env} environment")
        }
    }
}

package dev.matta.utilities

import dev.matta.enums.Environment

class HostingInfo {
    static Map info(Map map) {
        Map remote = [:]
        remote.allowAnyHosts = true
        switch (map.environment as Environment) {
            case Environment.DEV:
                remote.user = ""
                remote.name = ""
                remote.host = ""
                break
            case Environment.QA:
                remote.user = ""
                remote.name = ""
                remote.host = ""
                break
            case Environment.STAGING:
                remote.user = ""
                remote.name = ""
                remote.host = ""
                break
            case Environment.PRODUCTION:
                remote.user = ""
                remote.name = ""
                remote.host = ""
                break
            default:
                break
        }
        remote.identityFile = map.keyfile
        return remote
    }

    static String credentialsId(Environment env) {
        switch (env) {
            case Environment.DEV:
                return ''
            case Environment.QA:
                return ''
            case Environment.STAGING:
                return ''
            case Environment.PRODUCTION:
                return ''
            default:
                break
        }
    }
}

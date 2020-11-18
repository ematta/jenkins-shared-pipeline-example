package dev.matta.utilities

class SlackMessaging {
    static String serviceMessage(Map map) {
        def message
        def jobName = map.currentBuild.projectName
        def emoji = map.currentBuild.currentResult
        def version = map.get('version', '(version missing)')
        def url = map.currentBuild.absoluteUrl.replace("/job/", "/blue/organizations/jenkins/${jobName}/detail/")
        message = """
*BUILD*: :${Constants.EMOJI[emoji]}: ${map.service as String}:${version} :${Constants.EMOJI[emoji]}:
${url}
"""
        return message
    }

    static String testingMessage(Map map) {
        def jobName = map.currentBuild.projectName
        def emoji = map.currentBuild.currentResult
        def url = map.currentBuild.absoluteUrl.replace("/job/", "/blue/organizations/jenkins/${jobName}/detail/")
        def service = map.get('service', 'Test Framwork')
        return """
*TEST*: :${Constants.EMOJI[emoji]}: ${service} :${Constants.EMOJI[emoji]}:
*ENV*: ${map.environment}
${url}
"""
    }

    static String deploymentMessage(Map map) {
        def jobName = map.currentBuild.projectName
        def emoji = map.currentBuild.currentResult
        def url = map.currentBuild.absoluteUrl.replace("/job/", "/blue/organizations/jenkins/${jobName}/detail/")
        return """
*DEPLOYED*: :${Constants.EMOJI[emoji]}: ${map.component}:${map.version} :${Constants.EMOJI[emoji]}:
*ENV*: ${map.environment}
${url}
"""
    }
}

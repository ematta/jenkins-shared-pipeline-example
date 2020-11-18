package dev.matta.utilities

class Constants {
    static final String SLACK_TOKEN = ''
    static final String DOCKER_URL = ''
    static final String DOCKER_ECR = ''
    static final String DOCKER_CREDENTIALS_ID = ''
    static final String GITHUB_CREDENTIALS_ID = ''
    static final String VERSION = 'git describe --abbrev=0 --tags'
    static final String BRANCH = 'git rev-parse --abbrev-ref HEAD'
    static final String NODE_VERSION_SCRIPT = """npx yarn env | grep npm_package_version | cut -d ':' -f 2 | sed 's/[",\\,]//g'"""
    static final COLOR_MAP = [
        SUCCESS : 'good',
        FAILURE : 'danger',
        UNSTABLE: 'warning'
    ]

    static final EMOJI = [
        SUCCESS : 'tada',
        UNSTABLE: 'lightning',
        FAILURE : 'octagonal_sign'
    ]
}

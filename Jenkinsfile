def CONTAINER_NAME = "calculator"
def ENV_NAME = ${ getEnvName(env.BRANCH_NAME) }
def CONTAINER_TAG = ${ getTag(env.BUILD_NUMBER, env.BRANCH_NAME) }
def HTTP_PORT = ${getHTTPPort(env.BRANCH_NAME) }


node {

    stage('Initialize') {
        def dockerHome = tool 'DockerLatest'
        def mavenHome = tool 'MavenLatest'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build with test') {
        sh "mvn clean install"
    }

    stage('Sonarqube Analysis') {
        withSonarQubeEnv('SonarQubeLocalServer') {
            sh " mvn sonar:sonar -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true"
        }
        timeout(time: 1, unit: 'MINUTES') {
            def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
            if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    }

    stage("Image Prune") {
        imagePrune(CONTAINER_NAME)
    }

    stage('Image Build') {
        imageBuild(CONTAINER_NAME, CONTAINER_TAG)
    }

    stage('Push to Docker Registry') {
        withCredentials([usernamePassword(credentialsId: 'DockerhubCredentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
        }
    }

    stage('Run App') {
        withCredentials([usernamePassword(credentialsId: 'DockerhubCredentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            runApp(CONTAINER_NAME, CONTAINER_TAG, USERNAME, HTTP_PORT, ENV_NAME)

        }
    }

}

def imagePrune(containerName) {
    try {
        sh "docker image prune -f"
        sh "docker stop $containerName"
    } catch (ignored) {
    }
}

def imageBuild(containerName, tag) {
    sh "docker build -t $containerName:$tag  -t $containerName --pull --no-cache ."
    echo "Image build complete"
}

def pushToImage(containerName, tag, dockerUser, dockerPassword) {
    sh "docker login -u $dockerUser -p $dockerPassword"
    sh "docker tag $containerName:$tag $dockerUser/$containerName:$tag"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort,envName) {
    sh "docker pull $dockerHubUser/$containerName"
    sh "docker run --rm --env --env SPRING_ACTIVE_PROFILES=$envName -d -p $httpPort:$httpPort --name $containerName $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPort} (http)"
}

String getEnvName(String branchName) {
    if (branchName == 'main') {
        return 'prod'
    }
    return (branchName == 'develop') ? 'uat' : 'dev'
}

String getHTTPPort(String branchName) {
    if (branchName == 'main') {
        return '9003'
    }
    return (branchName == 'develop') ? '9002' : '9001'
}

String getTag(String buildNumber, String branchName) {
    if (branchName == 'main') {
        return buildNumber + '-unstable'
    }
    return buildNumber + '-stable'
}
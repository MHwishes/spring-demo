node('docker') {
    checkout scm
    stage('Build') {
        docker.image('java:8-jdk-alpine').inside {
            sh 'java -version'
        }
    }
}
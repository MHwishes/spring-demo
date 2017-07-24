pipeline {
  agent any
  stages {
    stage('Unit Tests') {
      tools {
        maven 'maven 3.5.0'
      }
      steps {
        sh './mvnw test'
      }
      post {
        failure {
          cleanWs()
        }
      }
    }
  }
}
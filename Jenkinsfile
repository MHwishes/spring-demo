pipeline {
  agent any
  stages {
    stage('Unit Tests') {
      tools {
        maven 'maven 3.5.0'
      }
      steps {
        sh 'mvn test -Dtest=PersonControllerTest'
      }
      post {
        failure {
          cleanWs()
        }
      }
    }
    state('intergation Test'){
     tools {
            maven 'maven 3.5.0'
          }
          steps {
            sh 'mvn test -Dtest=DemoTest'
          }
          post {
            failure {
              cleanWs()
            }
          }
    }
  }
}
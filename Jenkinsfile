pipeline {
  agent any
  stages {

    stage('Unit Tests') {
      tools {
        maven 'maven 3.5.0'
      }
      steps {
        sh 'mvn test -P pro -Dtest=PersonControllerTest'
      }
      post {
        failure {
          cleanWs()
        }
      }
    }

    stage('Integration Tests'){
       tools {
           maven 'maven 3.5.0'
         }
         steps {
            sh 'mvn test  -P pro -Dtest=DemoTest'
         }
         post {
            failure {
              cleanWs()
            }
         }
     }

  }
}
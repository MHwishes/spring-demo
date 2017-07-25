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

    stage('Integration Tests'){
       tools {
           maven 'maven 3.5.0'
         }
         steps {
            sh 'mvn test  -Dtest=DemoTest'
         }
         post {
            failure {
              cleanWs()
            }
         }
     }


    stage('container Tests'){
           steps{
                 checkout scm
                 sh '/usr/local/bin/docker-compose -f docker-compose-test.yml up'
            }
           post {
                failure {
                  cleanWs()
                }
             }
         }

  }
}
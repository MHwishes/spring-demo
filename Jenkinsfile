
pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'sh ./assembly/setup.sh'
      }
      post {
        failure {
          cleanWs()
        }
      }
    }
    stage('Unit Tests') {

      steps {
       echo "hello"
      }
      post {
        failure {
          cleanWs()
          }
      }
    }
  }
}

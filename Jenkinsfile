node {
    stage 'Building image'
    git 'https://github.com/MHwishes/spring-demo.git' // checks out Dockerfile
    def myEnv = docker.build 'cqueiroz:snapshot'
    sh "docker-compose up -d"
    sh "./mvnw test"
   }
pipeline {
    agent any
    tools {
        maven 'M3'
    }
    environment {
        PATH = "C:\\WINDOWS\\SYSTEM32;C:\\Program Files\\Docker\\Docker\\resources\\bin"
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/nsatyaprakash/spring-openai']])
                // bat 'git clone git@github.com:nsatyaprakash/spring-openai.git'
                bat """
                    dir
                    mvn clean install
                """
            }
        }
        stage('Build Docker image') {
            steps {
                bat """
                    docker build -t dockernamer/spring-openai .
                """
            }
        }
        stage('Push Docker image') {
            steps {
                withCredentials([string(credentialsId: 'dockrpwd', variable: 'dockerpwd')]) {
                    bat """
                        docker login -u dockernamer -p ${dockerpwd}
                        docker push dockernamer/spring-openai
                    """
                }
            }
        }
    }
}

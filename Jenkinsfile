pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/priyasinghdev5/ecommerce-microservices.git'
            }
        }

        stage('Build Services') {
            steps {
                sh '''
                mvn clean install -DskipTests
                '''
            }
        }

        stage('Build Docker Images') {
            steps {
                sh '''
                docker-compose build
                '''
            }
        }

        stage('Run Containers') {
            steps {
                sh '''
                docker-compose down || true
                docker-compose up -d
                '''
            }
        }
    }
}
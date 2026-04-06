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
                cd api-gateway && mvn clean install -DskipTests
                cd ../auth-service && mvn clean install -DskipTests
                cd ../product-service && mvn clean install -DskipTests
                cd ../product-detail-service && mvn clean install -DskipTests
                cd ../cart-service && mvn clean install -DskipTests
                cd ../order-service && mvn clean install -DskipTests
                cd ../inventory-service && mvn clean install -DskipTests
                cd ../payment-service && mvn clean install -DskipTests
                cd ../notification-service && mvn clean install -DskipTests
                cd ../eureka-server && mvn clean install -DskipTests
                cd ../config-server && mvn clean install -DskipTests
                '''
            }
        }

        stage('Build Docker Images') {
            steps {
                sh '''
                cd docker
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
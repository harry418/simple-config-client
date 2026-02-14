pipeline {
    agent any
    tools {
        maven 'maven'   // Use the default Maven installation name
        jdk 'jdk'       // Use the default JDK installation name (if configured)
     }


    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/your-repo.git'
            }
        }

        stage('Clean Artifacts') {
            steps {
                echo 'Cleaning old JAR/WAR files...'
                sh 'mvn clean'
                sh 'rm -f target/*.jar target/*.war || true'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }

        stage('Run Dev Environment') {
            steps {
                echo 'Starting Spring Boot app with dev profile...'
                sh 'mvn spring-boot:run -Dspring-boot.run.profiles=dev &'
                // Give app some time to start
                sh 'sleep 20'
            }
        }

        stage('Test on Dev') {
            steps {
                echo 'Running test cases on dev environment...'
                sh 'mvn test -Dspring-boot.run.profiles=dev'
            }
        }

        stage('Stop Dev Environment') {
            steps {
                echo 'Stopping Spring Boot dev process...'
                sh "pkill -f 'spring-boot:run.*dev' || true"
            }
        }

        stage('Run Prod Environment') {
            steps {
                echo 'Starting Spring Boot app with prod profile...'
                sh 'mvn spring-boot:run -Dspring-boot.run.profiles=prod &'
                sh 'sleep 20'
            }
        }

        stage('Stop Prod Environment') {
            steps {
                echo 'Stopping Spring Boot prod process...'
                sh "pkill -f 'spring-boot:run.*prod' || true"
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'Cleaning up any leftover Spring Boot processes...'
            sh "pkill -f 'spring-boot:run' || true"
        }
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check logs.'
        }
    }
}

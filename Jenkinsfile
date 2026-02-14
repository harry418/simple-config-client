pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'Java_17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/harry418/simple-config-client.git'
            }
        }

        stage('Clean Artifacts') {
            steps {
                bat 'mvn clean'
                bat '''
                if exist target\\*.jar del /Q target\\*.jar
                if exist target\\*.war del /Q target\\*.war
                '''
            }
        }

        stage('Build') {
            steps {
                bat 'mvn install -DskipTests'
            }
        }

        stage('Test on Dev Profile') {
            steps {
                bat 'mvn test -Dspring-boot.run.profiles=dev'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
                archiveArtifacts artifacts: 'target\\*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Build and tests completed successfully!'
        }
        failure {
            echo 'Build failed. Check logs for details.'
        }
    }
}

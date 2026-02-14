pipeline {
    agent any

    tools {
        maven 'maven'       // Must match Maven installation name in Jenkins
        jdk 'Java_17'       // Must match JDK installation name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/harry418/simple-config-client.git'
            }
        }

        stage('Clean Artifacts') {
            steps {
                echo 'Cleaning old JAR/WAR files...'
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

        stage('Run Dev Environment') {
            steps {
                echo 'Starting Spring Boot app with dev profile...'
                bat 'mvn spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-Dspring.pid.file=dev.pid"'
            }
        }

        stage('Test on Dev') {
            steps {
                echo 'Running test cases on dev environment...'
                bat 'mvn test -Dspring-boot.run.profiles=dev'
            }
        }

        stage('Stop Dev Environment') {
            steps {
                echo 'Stopping Spring Boot dev process...'
                bat '''
                if exist dev.pid (
                    for /F %%p in (dev.pid) do taskkill /F /PID %%p
                    del dev.pid
                )
                '''
            }
        }

        stage('Run Prod Environment') {
            steps {
                echo 'Starting Spring Boot app with prod profile...'
                bat 'mvn spring-boot:run -Dspring-boot.run.profiles=prod -Dspring-boot.run.jvmArguments="-Dspring.pid.file=prod.pid"'
            }
        }

        stage('Stop Prod Environment') {
            steps {
                echo 'Stopping Spring Boot prod process...'
                bat '''
                if exist prod.pid (
                    for /F %%p in (prod.pid) do taskkill /F /PID %%p
                    del prod.pid
                )
                '''
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
        always {
            echo 'Cleaning up any leftover Spring Boot processes...'
            bat '''
            if exist dev.pid (
                for /F %%p in (dev.pid) do taskkill /F /PID %%p
                del dev.pid
            )
            if exist prod.pid (
                for /F %%p in (prod.pid) do taskkill /F /PID %%p
                del prod.pid
            )
            '''
        }
    }
}

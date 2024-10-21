pipeline {
    agent any

    environment {
        APP_NAME = "projetdevops"
        RELEASE = "1.0.0"
        GIT_REPO = 'https://github.com/abderrahmenzarrouk/5ARCTIC6-G5-FOYER.git'
        BRANCH = 'AbderrahmenZarrouk-5Arctic6-G5'
        DOCKER_USER = "zarroukabderrahmen"
        DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning GitHub repository...'
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Clean') {
            steps {
                echo 'Cleaning the project...'
                sh 'mvn clean'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project'
                sh 'mvn package -DskipTests'
            }
        }

        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }

                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }

        }
    }

    post {
        failure {
            echo 'Build failed, sending email...'
            mail to: 'zarrouk.abderrhmen@gmail.com',
                 subject: "Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                 body: "Build ${env.BUILD_NUMBER} failed for ${env.JOB_NAME}. Check the logs at ${env.BUILD_URL}"
        }

        success {
            echo 'Build succeeded.'
        }
    }
}

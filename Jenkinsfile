pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/abderrahmenzarrouk/5ARCTIC6-G5-FOYER/tree/AbderrahmenZarrouk-5Arctic6-G5'
        BRANCH = 'AbderrahmenZarrouk-5Arctic6-G5' // You can change this to the branch you want to monitor
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

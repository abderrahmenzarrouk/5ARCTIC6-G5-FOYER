pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'foyer2'  // Repository name only
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials' // Docker Hub credentials ID
        GIT_CREDENTIALS_ID = 'github-token' // GitHub Token credentials ID
        DOCKER_USERNAME = 'fourat02'
    }
    stages {
        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Build') {
            steps {
                sh 'git config --global http.postBuffer 524288000' // 500 MB buffer size
                
                retry(3) {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/foufou']],
                        extensions: [
                            [$class: 'CloneOption', depth: 1]
                        ],
                        userRemoteConfigs: [[credentialsId: 'github-token', url: 'https://github.com/abderrahmenzarrouk/5ARCTIC6-G5-FOYER.git']]
                    ])
                }

                echo "Building project..."
                sh "mvn clean package"
                sh "mvn test"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image..."
                    sh "docker build -t ${DOCKER_USERNAME}/${DOCKER_IMAGE}:latest ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    script {
                        def imageTag = "${DOCKER_USERNAME}/${DOCKER_IMAGE}:latest"
                        echo "Logging in to Docker Hub..."
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                        echo "Pushing Docker image..."
                        sh "docker push ${imageTag}"
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}


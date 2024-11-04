pipeline {
    agent any

    tools {
        maven 'mavenpi'  // Assurez-vous que c'est le bon nom de votre installation Maven
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/abderrahmenzarrouk/5ARCTIC6-G5-FOYER.git',
                    branch: 'alaeddine-selmi-arctic6-G5',
                    credentialsId: 'ala'  // Vérifiez que cet ID de credentials est correct
            }
        }

        stage('Build') {
            steps {
                sh 'ls -la'
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'alala') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=alaala -Dsonar.host.url=http://192.168.12.148:9000'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Jacoco Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t spring-boot-app:latest .'
                sh 'docker tag spring-boot-app:latest alaselmi/devops:latest'
                sh 'docker images'
            }
        }

        stage('Docker Compose Deploy') {
            steps {
                script {
                    // Exécuter Docker Compose pour déployer l'application
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockre') {
                        sh 'docker push alaselmi/devops:latest'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Déploiement en cours...'
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé.'
        }
        success {
            echo 'Le build a réussi.'
        }
        failure {
            echo 'Le build a échoué.'
        }
    }
}

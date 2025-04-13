pipeline {
    agent any
    environment {
    AWS_ACCESS_KEY_ID     = credentials('aws-access-key-id')
    AWS_SECRET_ACCESS_KEY = credentials('aws-secret-access-key')
    AWS_REGION = 'ap-southeast-2'
    TF_DIR = 'terraform-eks'
  }
    stages {
        stage('Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/Aniketrmore/jenkins-project.git'
            }
        }   
        stage('Destroy') {   
            steps {
                dir("${env.TF_DIR}") {
                sh '''
                      terraform destroy --auto-approve \
                      -var="aws_access_key=${AWS_ACCESS_KEY_ID}" \
                      -var="aws_secret_key=${AWS_SECRET_ACCESS_KEY}"
                   '''
                }   
            }
        }
    }
}

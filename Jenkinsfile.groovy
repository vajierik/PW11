pipeline {
    agent {label 'docker'}
    stages {
        stage('check-site'){
            steps {
                script{
                    sh './scripts/check-site.sh'
                }
            }
        }
    }
    post {
        failure {
            mail to: 'otr580@gmail.com',
            subject: "Pipeline failed: ${currentBuild.fullDisplayName}",
            body: "Build failed ${env.BUILD_URL}"
        }
    }
}

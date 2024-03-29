#!/usr/bin/env groovy
pipeline {
  agent { 
    docker {
            image 'nginx'
            args '-u root'
        }
  }
  
  stages {
    stage('Run on index.html changes') {
      when {
        changeset 'index.html'
      }

      stages {
        stage('Build') {
          steps {
            sh 'docker build -t my-nginx .'
          }
        }

        stage('Run') {
          steps {
            sh 'docker run -d -p 9889:80 --name my-nginx-container my-nginx'
          }
        }

        stage('Test available') {
          steps {
            sh 'curl -s localhost:9889 | grep "Hello world"'
          }
        }

        stage('Compare MD5 Sum') {
          steps {
            sh 'curl -s localhost:9889 | md5sum | grep $(md5sum index.html | awk \'{print $1}\')'
          }
        }
      }

      post {
        always {
          sh 'docker stop my-nginx-container'
          sh 'docker rm my-nginx-container'
        }

        failure {
            mail to: 'otr580@gmail.com',
            subject: "Pipeline failed: ${currentBuild.fullDisplayName}",
            body: "Build failed ${env.BUILD_URL}"
        }
      }
    }
  }
}

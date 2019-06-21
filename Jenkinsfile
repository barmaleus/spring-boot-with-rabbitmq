pipeline {
    agent any

    stages {
        stage('Checkout'){
          //git branch: '${branchToBuild}', url: 'GITURL'
           // git checkout master
        }
        stage('Build') {
            steps {
                echo 'Building..'
                mvn clean install
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
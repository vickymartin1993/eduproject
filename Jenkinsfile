pipeline {
    agent none
    stages {
        stage('install puppet on slave') {
            agent { label 'slave'}
            steps {
                echo 'Install Puppet'
                sh "sudo apt-get install -y wget"
                sh "wget https://apt.puppetlabs.com/puppet-release-bionic.deb"
                sh "sudo dpkg -i puppet-release-bionic.deb"
                sh "sudo apt update"
                sh "sudo apt install -y puppet-agent"
            }
        }

        stage('configure and start puppet') {
            agent { label 'slave'}
            steps {
                echo 'configure puppet'
                sh "mkdir -p /etc/puppetlabs/puppet"
                sh "if [ -f /etc/puppetlabs/puppet/puppet.conf ]; then sudo rm -f /etc/puppetlabs/puppet.conf; fi"
                sh "echo '[main]\ncertname = node1.local\nserver = puppet' >> ~/puppet.conf"
                sh "sudo mv ~/puppet.conf /etc/puppetlabs/puppet/puppet.conf"
                echo 'start puppet'
                sh "sudo systemctl start puppet"
                sh "sudo systemctl enable puppet"
            }
        }


        stage('Sign in puppet certificate') {
            agent{ label 'master'}
            steps {
              catchError {
                sh "sudo /opt/puppetlabs/bin/puppet cert sign node1.local"
              }
            }
        }


        stage('Install Docker-CE on slave through puppet') {
            agent{ label 'master'}
            steps {
                sh "sudo /opt/puppetlabs/bin/puppet apply /home/edureka/site.pp"
            }
        }

        stage('Git Checkout') {
            agent{ label 'slave'}
            steps {
                sh "if [ ! -d '/home/edureka/devops-webapp' ]; then git clone https://github.com/naveenpurohit2003/devops-webapp.git /home/edureka/devops-webapp ; fi"
                sh "cd /home/edureka/devops-webapp && git checkout master"
            }
        }

        stage('Docker Build and Run') {
            agent{ label 'slave'}
            steps {
                sh "sudo docker rm -f webapp || true"
                sh "cd /home/edureka/devops-webapp && sudo docker build -t test ."
                sh "sudo docker run -it -d --name webapp -p 8080:80 test"
            }
        }

        stage('Check if selenium test run') {
            agent{ label 'slave'}
            steps {
                sh "cd /home/edureka/devops-webapp && java -jar devops-webapp.jar"
            }
            post {
                failure {
                    sh "sudo docker rm -f webapp"
                }
            }
        }
    }
}

pipeline {
	agent any

    stages {

 
		stage('Checkout'){
			steps {
				//CHECK OUT PROCESS: Checkout code from the Git Repository
				git url: 'https://github.com/IKCONDev/ums-authentication.git'
			}
		}

 

		stage('Build and Test'){
			steps {
                           script{
                                 //Set JAVA_HOME
                                 bat '''
                                  set JAVA_HOME=C:/Program Files/Java/jdk-11
                                  mvn clean package
                                  mvn test
                                 '''
                             }
			}
		}

 

        stage('Moving the WAR File to Tomcat Server') {
            steps {
                bat '''
				
		     echo War file moved and deployed sucessfully!
                     sc stop tomcat9
                     echo tomcat stopped successfully!
                     
                     ping 127.0.0.1 -n 21 > nul

                     sc start tomcat9
                     echo tomcat started successfully!

		    '''
            }
        }
    }
}

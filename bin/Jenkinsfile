BINDINGS:{

	/*Send an email notification to the following recipients when the pipeline fails.*/
	failureEmailReceiver = """
		tormavictor@gmail.com;
		"""

	/*
	Send a summary report to the following recipients when the tests finish running.
	The email is sent regardless of Failed/Passed/Skipped ratio of the individual tests within the job.
	Jenkins does not analize the actual results. The report requires further analysis by human eye.	*/
	completedEmailReceiver = """		
		tormavictor@gmail.com;
		squeezoure@gmail.com;
		"""
}


pipeline {
    agent none
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        timeout(time: 6, unit: 'HOURS')
    }
    stages {

		stage('GUI Test') {
			agent {
				docker {
					image 'myDockerImage'
					label 'dind'
					args '-e "CNF_KEY_LOCATION=/home/JENKINS/selenium_keys"'
					customWorkspace '/home/test_framework'
				}
			}

			steps {

				checkout scm

				echo "=== INFO ==="
				echo "Running test: ${suite_name}"
				sh 'printenv'
				sh 'whoami'
				sh 'pwd'
				sh 'ls -lAtr'
				sh 'google-chrome --version'
				sh 'chromedriver --version'
				sh 'firefox --version'
				sh 'geckodriver --version'

				echo "=== TEST ==="
                withCredentials([usernamePassword(credentialsId: 'credentialsID', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'mvn -s settings.xml -B -Dsuite_name=${suite_name} -Doracle.jdbc.autoCommitSpecCompliant=false clean integration-test'
                }

				echo "=== RESULTS ==="
				sh 'ls -lAtr'
				stash name: 'Results', useDefaultExcludes: false

			}
			post {
                success {
                    echo "GUI Test stage completed."
					unstash "Results"
					emailext (
						mimeType: "text/html",
						attachLog: true,
						attachmentsPattern: "test-output/emailable-report.html",
						to: "${completedEmailReceiver}",
						subject: "PCE Test Completed - ${suite_name}",
						body: '${FILE,path="/home/PCE_QA/test-output/emailable-report.html"}'
					)
                }
                failure {
                    echo "GUI Test stage failed."
                }

            }

		}

    }
    post {
        success {
            echo "Your Propel pipeline has successfully completed."
        }
        failure {
			echo "An error occurred in your Propel pipeline."
			emailext (
				mimeType: "text/html",
				attachLog: true,
				to: "${failureEmailReceiver}",
				subject: "PCE Pipeline Failed - ${suite_name}",
				body: "An error has occurred in your Jenkins pipeline.<br>Click to view details: ${env.BUILD_URL}"
			)
        }

    }
}

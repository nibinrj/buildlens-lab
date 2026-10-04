// Builds the lab project on the BuildLens agent and reports every build to BuildLens.
// The 'buildlens' library is configured in BuildLens's jenkins/casc/jenkins.yaml (folder shared-library/).
//
// Scripted Pipeline because timedStage wraps stage(), which Declarative syntax does not allow.
// The finally block plays the role of Declarative's post { always }.
@Library('buildlens') _

node('maven') {
    timestamps {
        try {
            timedStage('Checkout') {
                checkout scm
            }
            timedStage('Build') {
                sh 'mvn -B -ntp -DskipTests package'
            }
            timedStage('Test') {
                // Test failures make the build UNSTABLE through the junit step, not FAILED.
                sh 'mvn -B -ntp -Dmaven.test.failure.ignore=true verify'
                junit allowEmptyResults: true,
                      testResults: '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
            }
        } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
            // finally runs before Jenkins records the result, so set it here for reportBuild.
            currentBuild.result = 'ABORTED'
            throw e
        } catch (e) {
            currentBuild.result = 'FAILURE'
            throw e
        } finally {
            reportBuild()
        }
    }
}

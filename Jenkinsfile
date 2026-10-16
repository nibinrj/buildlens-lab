// Scripted pipeline; all flakiness in flaky-lab is explicitly INJECTED and seeded.
@Library('buildlens') _
node('maven') {
    timestamps {
        try {
            timedStage('Checkout') { deleteDir(); checkout scm }
            timedStage('Build') { sh 'mvn -B -ntp -DskipTests package' }
            quarantineAwareTests(repo: 'nibinrj/buildlens-lab',
                mavenArgs: "-Dbuildlens.flaky.seed=${env.BUILD_NUMBER} -Dbuildlens.flaky.clockMillis=${env.BUILD_NUMBER}000")
        } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
            currentBuild.result = 'ABORTED'; throw e
        } catch (e) {
            currentBuild.result = 'FAILURE'; throw e
        } finally {
            reportBuild(repo: 'nibinrj/buildlens-lab',
                reports: '**/target/surefire-reports/TEST-*.xml',
                quarantineReports: '**/target/surefire-reports-quarantine/TEST-*.xml')
        }
    }
}

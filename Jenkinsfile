// Scripted pipeline; flakiness and configurable slowdown are explicitly INJECTED.
@Library('buildlens') _
properties([parameters([
    string(name: 'SLOW_MILLIS', defaultValue: '0', description: 'Controlled injected delay, 0..10000 milliseconds')
])])
node('maven') {
    long agentStartedAt = System.currentTimeMillis()
    timestamps {
        try {
            String slow = params.SLOW_MILLIS?.trim() ?: '0'
            if (!(slow ==~ /[0-9]{1,5}/) || slow.toInteger() > 10000) {
                error('SLOW_MILLIS must be an integer from 0 to 10000')
            }
            timedStage('Checkout') { deleteDir(); checkout scm }
            timedStage('Build') { sh 'mvn -B -ntp -DskipTests package' }
            quarantineAwareTests(repo: 'nibinrj/buildlens-lab',
                mavenArgs: "-Dbuildlens.flaky.seed=${env.BUILD_NUMBER} -Dbuildlens.flaky.clockMillis=${env.BUILD_NUMBER}000 -Dbuildlens.slow.ms=${slow}")
        } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
            currentBuild.result = 'ABORTED'; throw e
        } catch (e) {
            currentBuild.result = 'FAILURE'; throw e
        } finally {
            reportBuild(repo: 'nibinrj/buildlens-lab', agentStartedAt: agentStartedAt,
                reports: '**/target/surefire-reports/TEST-*.xml',
                quarantineReports: '**/target/surefire-reports-quarantine/TEST-*.xml')
        }
    }
}

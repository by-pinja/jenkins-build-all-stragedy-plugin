library 'jenkins-ptcs-library@feature/support-for-asssets-on-release'

podTemplate(label: pod.label,
  containers: pod.templates + [
    containerTemplate(name: 'maven', image: 'maven:3.5.3', ttyEnabled: true, command: '/bin/sh -c', args: 'cat')
  ]
) {
    def project = 'jenkins-build-all-stragedy-plugin'

    node(pod.label) {
        stage('Checkout') {
            checkout scm
        }
        stage('Build') {
            container("maven") {
                sh """
                    mvn install -Dmaven.test.skip=true
                    cp ./target/build-everything-strategy.hpi $WORKSPACE/build-everything-strategy.hpi
                """

                archiveArtifacts allowEmptyArchive: true, artifacts: "build-everything-strategy.hpi"

                if (env.TAG_NAME && env.TAG_NAME != "null" && env.BRANCH_NAME == env.TAG_NAME)
                {
                    publishAssetToRelease("$WORKSPACE/jenkins-build-everything-strategy-plugin/target/build-everything-strategy.hpi")
                }
            }
        }
    }
  }

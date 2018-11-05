library 'jenkins-ptcs-library@feature/support-for-asssets-on-release'

podTemplate(label: pod.label,
  containers: pod.templates + [
    containerTemplate(name: 'maven', image: 'maven', ttyEnabled: true, command: '/bin/sh -c', args: 'cat')
  ]
) {
    def project = 'fortum-gateway-embedded'

    node(pod.label) {
        stage('Checkout') {
            checkout scm
        }
        stage('Build') {
            container("maven") {
                sh """
                    git clone https://github.com/AngryBytes/jenkins-build-everything-strategy-plugin.git
                    cd jenkins-build-everything-strategy-plugin
                    mvn install
                """

                archiveArtifacts allowEmptyArchive: true, artifacts: "$WORKSPACE/jenkins-build-everything-strategy-plugin/target/build-everything-strategy.hpi"

                if (env.TAG_NAME && env.TAG_NAME != "null" && env.BRANCH_NAME == env.TAG_NAME)
                {
                    publishAssetToRelease("$WORKSPACE/jenkins-build-everything-strategy-plugin/target/build-everything-strategy.hpi")
                }
            }
        }
    }
  }
[CmdLetBinding()]
Param()

docker run -it -v "$PSScriptRoot/:/var/build/" -w="/var/build/" maven:3.5.3 /bin/sh -c "mvn install -Dmaven.test.skip=true"

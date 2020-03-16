#!/bin/bash
set -x
cd /mnt/RetroRoute
mvn clean install
printf "packaged project"
cp /mnt/RetroRoute/target/RetroRoute-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
printf "moved package to tomcat"
exit
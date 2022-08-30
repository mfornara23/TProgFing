#!/bin/sh
#
#Build and install Logic dependency
mvn -f ./corona-ticket-uy-central/pom.xml clean install
#
#Build Workstation JAR
mvn -f ./corona-ticket-uy-workstation/pom.xml clean install
#
#Build Web WAR
mvn -f ./corona-ticket-web/pom.xml clean install
#################
#Move war to the root
rm -rf ./war
mkdir ./war
mv ./corona-ticket-web/target/corona-ticket-web.war ./war/
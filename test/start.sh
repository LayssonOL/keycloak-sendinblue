#!/bin/bash
# Setting the JAVA path
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
# build extension without SNAPSHOT suffix
mvn clean package -e -DskipTests -DprojectVersion=docker -Dchangelist=
if [[ "$?" -ne 0 ]] ; then
  echo 'could not run maven package'; exit $rc
fi

# get keycloak version from pom
KEYCLOAK_VERSION=$(mvn help:evaluate -Dexpression=keycloak.version -q -DforceStdout)
# KEYCLOAK_VERSION=latest

# start docker
# KEYCLOAK_VERSION=$KEYCLOAK_VERSION docker-compose up --build --detach
KEYCLOAK_VERSION=$KEYCLOAK_VERSION docker-compose -f ../../docker-compose.dev.yml up --detach

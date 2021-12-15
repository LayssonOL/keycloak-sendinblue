#!/bin/bash
# get keycloak version from pom
KEYCLOAK_VERSION=$(mvn help:evaluate -Dexpression=keycloak.version -q -DforceStdout)

# start docker
# KEYCLOAK_VERSION=$KEYCLOAK_VERSION docker-compose down
KEYCLOAK_VERSION=$KEYCLOAK_VERSION docker-compose -f ../../docker-compose.dev.yml down --rmi all --remove-orphans

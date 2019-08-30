#!/bin/bash
PROJECT_VERSION=$(./mvnw -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)

if [[ "${TRAVIS_PULL_REQUEST}" == "false" && $PROJECT_VERSION == *"-SNAPSHOT" && "$TRAVIS_BRANCH" == "master" ]]; then
	./mvnw deploy jacoco:report coveralls:report --settings .mvn/settings.xml
fi

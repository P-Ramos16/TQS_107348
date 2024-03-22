#!/bin/bash

token=sqp_c7bdc710ae6c34019f82b41a707f89c8d414c643

mvn clean test jacoco:report sonar:sonar -Pcoverage -Dsonar.host.url=http://localhost:9000 -Dsonar.projectKey=cars -Dsonar.projectName='Cars' -Dsonar.token=$token
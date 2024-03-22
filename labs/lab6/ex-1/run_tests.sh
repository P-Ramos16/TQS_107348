#!/bin/bash

token=sqp_abe8cf513074684a38ae8cdc80f31d2bc3d701e7

mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.projectKey=euromillions -Dsonar.projectName='euromillions' -Dsonar.token=$token
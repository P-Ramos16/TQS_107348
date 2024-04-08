FROM maven:3.8.5-openjdk-17

WORKDIR /backend

COPY . .


ENTRYPOINT ["mvn", "verify", "-Dtest=!CucumberSeleniumTests","sonar:sonar", "-Pcoverage", "-Dsonar.host.url=http://sonarqube:9000", "-Dsonar.projectKey=Road-Roam", "-Dsonar.projectName='Road Roam'", "-Dsonar.token=sqp_6285eaef09f5a94958aa6214cc89fe94673e74ae", "spring-boot:run"]
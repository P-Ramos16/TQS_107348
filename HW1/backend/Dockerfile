FROM maven:3.8.5-openjdk-17

WORKDIR /backend

COPY . .


ENTRYPOINT ["mvn", "verify", "-Dtest=!CucumberSeleniumTests","sonar:sonar", "-Pcoverage", "-Dsonar.host.url=http://sonarqube:9000", "-Dsonar.projectKey=Road-Roam", "-Dsonar.projectName='Road Roam'", "-Dsonar.token=sqp_68990efce4fe586d2ff29652fe8270b5fe2349b7", "spring-boot:run"]
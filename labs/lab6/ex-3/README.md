# Lab6 Ex3
### IES My Sweet Home Quality gates and Code smell detection with Sonarqube

#### Ex a:
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/sonarqube_gate_conditions.png?raw=true" width="1000">

 For the quality gate, I decided in a set of conditions that follow spring boot's ideals for code readability, chain of command, de-duplication of code and vulnerability prevention.

 | Metric | Value | Why |
 | --- | --- | --- |
 | Issues | greather than 0 | Issues should not be allowed to pass to the main code (when found beforehand) |
 | Security Hotspots Reviewed | less than 100% | All the security issues must be seen, as this is a public application |
 | Coverage | less than 65% | Less relevant, but most of the outputs of the code should be covered |
 | Duplicated Lines | greather than 1% | Keeping the theme of cleanliness, the number of duplicated lines should be as small as possible |
 | Condition Coverage | less than 90% | Almost all of the endpoint's values should be testes as to not create problems for the end user |
 | Duplicated Blocks | greather than 0 | Again, duplicated code should be heavilly avoided |
 | Maintainability Rating | less than B | This code must be easily maintaned by someone other than the programmer that made it, as this is a group project |
 | Code Smells | greather than 20 | In Java code ettiquete is very important so few code smells should be introduced |
 | Major Issues | greather than 0 | No major issues should be uploaded into the main code |
 | Vulnerabilities | greather than 0 | Once again, no vulnerabilities can be introduced as this is a public project |
 | Technical Debt Ratio | greather than 30% | The final Debt Ratio should not be 30% as big as the time it took to write the code itself, meaning issues shouldn't take longer to fix than the code took to be created |

---

#### Ex b:

<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/small_report.png?raw=true" width="1000">

 An increment over the original code was introduced in order to see if Sonarqube would pick up intentional code smells.
 This increment was a new endpoint with corresponding service and data repository functionality.

---

 Sonarqube was added inside the docker-compose file and became part of the docker ecosystem of the application.
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/sonarqube_in_docker_compose.png?raw=true" width="1000">

---

 This was the initial screen before the changes.
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/sonarqube_coverage.png?raw=true" width="1000">


---

 And after the changes the Quality Gate was not passed, meaning the new code did not conform to the standards set earlier.
 These results were the same as predicted and a new refactoring would be needed in order to bring this new feature into the production environment. 
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/gate_fail.png?raw=true" width="1000">


---

 Multiple lines were added with Code Smells and medium sized issues for readability, maintainability and performance. 
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/coverage_on_new.png?raw=true" width="1000">
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/new_issues.png?raw=true" width="1000">
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/code_smells.png?raw=true" width="1000">
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/code_smells2.png?raw=true" width="1000">
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-3/uncovered.png?raw=true" width="1000">
# Lab6 Ex1
### Euromillions test coverage with sonarqube

#### Ex e - Has your project passed the defined quality gate?:
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-1/sonarqube_result.png?raw=true" width="1000">

---

 - The project itself just passed the gate with a final coverage of 80.0%, but for example, the BoundedSetOfNaturals class was completely covered, as well as most of the classes that implement the login of this project.
 - The CuponEuromillions and Main class were the least covered, with 35% and 0% coverage each.

<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-1/sonarqube_coverage.png?raw=true" width="1000">

---

 - As for issues, 24 low severity issues were found amounting to a estimated effort time of 1 hour and 18 minutes.

<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-1/sonarqube_issue_list.png?raw=true" width="1000">

---

 - One severe security issue was found due to the use of the "Random" Java class that as been proved to not be very good at generating safe pseudorandom numbers.

<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-1/sonarqube_security_issue.png?raw=true" width="1000">

---

 - The final ratings were considered good for the Reliability and Security evaluation.

<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-1/sonarqube_ratings.png?raw=true" width="1000">




| Issue | Problem description | How to solve |
| --- | --- | --- |
| Security Hotstop | The "Random" Java class should not be expected to generate "safe" numbers. | Assert that this code does not require safe numbers to be generated and that no security fault is created by the usage of this class. |
| Consistency - Bad Practice | The return type of this method should be an interface such as "List" rather than the implementation "ArrayList". | Change the return type of this class to a more generic implementation and alter the classes that use this functions to make sure they still work with the provided return value. |
| Consistency - Intentionality | Use assertNotEquals instead. | Utilize assertNotEquals instead of reverting the value in a assertEquals. |
| Maintainability - Intentionality | Avoid assigning loop counter values inside the loop body. | Refactor the code in order to not assign to this loop counter from within the loop body. |

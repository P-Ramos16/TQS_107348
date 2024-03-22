# Lab6 Ex2
### Cars test coverage with sonarqube

#### Ex a:
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-2/sonarqube_issue_list.png?raw=true" width="1000">

 - The Cars project itself only showed one simple issue, so the final technical debt found was only 2 minutes, meaning the estimated time to fix the issues found is only about 2 minutes of average work time.

---

#### Ex c:
<img src="https://github.com/P-Ramos16/TQS_107348/blob/main/labs/lab6/ex-2/sonarqube_coverage.png?raw=true" width="1000">

**_The total overall coverage was of 58.8%_**
**_The line coverage was of 70.0%_**
**_The condition coverage was of 27.8%_**
 - For the Car Controller and the Car Model, only 4 and 9 lines respectivally were not covered by the tests, but this still resulted in over 47% and 55% uncovered conditions on both cases;
 - This means that critical lines that lead to many diferent results were not tested as they should be;
 - The Main class acuses a low level of coverage, but this class does not implement any hand-made code, meaning that we can largely ignore this result;
 - As for the Car Service, 100% of the lines were covered, leading to no uncovered conditions.
 
 These values are not great, but prove that more tests should be added in order to cover most of the conditions possible in the code and achieve the coveted 80% coverage percentage;

 This also shows that a high line coverage does not mean a high condition coverage, and that specific lines should be prioritized as to cover as much conditions as possible.
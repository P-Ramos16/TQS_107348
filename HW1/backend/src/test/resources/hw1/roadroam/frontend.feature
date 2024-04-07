Feature: Bus Company Website  
  To allow a customer to find the prefered bus trip between two cities, the website must offer ways to search, list and book trips.


    Scenario: User wants to search trips

        Given the user accessed the frontend
        When the user enters the main page
        And selects the origin as "Aveiro"
        And selects the destination as "Leiria"
        And the user presses the search button
        Then the user should go to the trips list page


    Scenario: User select the trip

        Given the user accessed the trips list with route value "3"
        When the user selects the trip number "2"
        Then the user should go to the purchase page


    Scenario: User inputs his information

        Given the user accessed the purchase page with trip value "8" and currency as "USD"
        When the user inputs his first name as "Josefino"
        And the user inputs his last name as "Calças"
        And the user inputs his phone number as "919202384"
        And the user inputs his email as "jose@fino.com"
        And the user selects his credit card as "MasterCard"
        And the user inputs the number of people as "2"
        And the user selects the seat "12"
        And the user presses the purchase button
        Then the user should go to the confirmation page


    Scenario: User confirms the details

        Given the user accessed the receipt page with ticket value "1"
        Then the user should see the final value as "110.24"
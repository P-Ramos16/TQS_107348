Feature: Book a Flight
  The website must allow the user to book a flight through a simple travel agency.

  Scenario: Navigate the initial screen
    When I navigate to "https://blazedemo.com/"
    And I insert flight parameters from "Portland" to "Dublin"
    And I click submit
    Then I should see the message "Flights from Portland to Dublin:"
  
  Scenario: Choose a flight option
    When I navigate to "https://blazedemo.com/" at page "reserve.php"
    And I click on option number 3
    Then I should see the message "Total Cost:" and "914.76"

  
  Scenario: Enter your user information
    When I navigate to the "https://blazedemo.com/purchase.php" page
    And I enter my "inputName" as "Josefino"
    And I enter my "address" as "Rua de Baixo, n.º11"
    And I enter my "zipCode" as "2420-123"
    And I select my card type as "American Express"
    And I enter my "creditCardYear" as "2023"
    And I click purchase flight
    Then I should see the message "Amount" and the value "555 USD"
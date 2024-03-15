Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 14 March 2013
      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23 August 2014
      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01 January 2012
    When the customer searches for books published between 2013 and 2014
    Then 2 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'


  Scenario: Search by author
    Given I have a list of books
      | Title | Author |
      | Refactoring | Martin Fowler |
      | Patterns of Enterprise Application Architecture | Martin Fowler |
      | Domain-Driven Design | Eric Evans |
      | Clean Code | Robert C. Martin |
    When I search for books by author "Martin Fowler"
    Then I should find 2 books

  Scenario: Search by title
    Given I have a list of books
      | Title | Author |
      | Refactoring | Martin Fowler |
      | Patterns of Enterprise Application Architecture | Martin Fowler |
      | Domain-Driven Design | Eric Evans |
      | Clean Code | Robert C. Martin |
    When I search for books by title "Refactoring"
    Then I should find 1 books

  Scenario: Search by year
    Given I have a list of books
      | Title | Author | Date |
      | Refactoring | Martin Fowler | 2019-09-07T-15:50+00 |
      | Patterns of Enterprise Application Architecture | Martin | 2019-09-07T-15:50+00 |
      | Domain-Driven Design | Eric Evans | 2019-09-07T-15:50+00 |
      | Clean Code | Robert C. Martin | 2019-09-07T-15:50+00 |
    When I search for books by title "Refactoring"
    Then I should find 1 books
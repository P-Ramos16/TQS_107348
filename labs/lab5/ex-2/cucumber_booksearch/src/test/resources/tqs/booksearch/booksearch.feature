Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search by author
    Given I have a list of books
      | Title | Author | Date |
      | Refactoring | Martin Fowler | 2021-11-02-15:50+00 |
      | Patterns of Enterprise Application Architecture | Martin Fowler | 2018-06-21-15:50+00 |
      | Domain-Driven Design | Eric Evans | 2020-03-23-15:50+00 |
      | Clean Code | Robert C. Martin | 2021-11-02-15:50+00 |
    When I search for books by author "Martin Fowler"
    Then I should find 2 books

  Scenario: Search by title
    Given I have a list of books
      | Title | Author | Date |
      | Refactoring | Martin Fowler | 2021-11-02-15:50+00 |
      | Patterns of Enterprise Application Architecture | Martin | 2018-06-21-15:50+00 |
      | Domain-Driven Design | Eric Evans | 2020-03-23-15:50+00 |
      | Clean Code | Robert C. Martin | 2021-11-02-15:50+00 |
    When I search for books by title "Refactor"
    Then I should find 1 books

  Scenario: Search by date
    Given I have a list of books
      | Title | Author | Date |
      | Refactoring | Martin Fowler | 2021-11-02-15:50+00 |
      | Patterns of Enterprise Application Architecture | Martin | 2018-06-21-15:50+00 |
      | Domain-Driven Design | Eric Evans | 2020-03-23-15:50+00 |
      | Clean Code | Robert C. Martin | 2021-11-01-15:50+00 |
    When I search for books by the date "2020-03-21-15:50+00" to "2021-11-02-15:50+00"
    Then I should find 3 books
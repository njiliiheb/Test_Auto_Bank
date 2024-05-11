Feature: View each customer's accounts

  Scenario: View each customer's accounts
    Given the user is on home page
    When  the user hits customers
    And  hits search customers
    And  the user enters valid credentials of customers
    And  hits button search of account
    And  hits Accounts
    Then Accounts are displayed

  Scenario: user fail view each customer's accounts
    Given the user is on home page
    When  the user hits customers
    And  hits search customers
    And  the user enters invalid credentials of customers
    And  hits button search of account
    Then  the user should see a failure message of invalid name customers

  Scenario: user fail view each customer's accounts1
    Given the user is on home page
    When  the user hits customers
    And  hits search customers
    And  the user enters valid credentials of customers2
    And  hits button search of account
    And  hits Accounts
    Then  the user should see a failure message customer dont have accounts




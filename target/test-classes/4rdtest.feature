Feature: View the transactions of each account

  Scenario: View the transactions of each account
    Given the user is on home pages
    When  the user hits accounts
    And  the user enters valid credentials of account
    And  hits button search of id account
    Then transactions are displayed

  Scenario: fail view the transactions of each account
    Given the user is on home pages
    When  the user hits accounts
    And  the user enters invalid credentials of account
    And  hits button search of id account
    Then the user should see a failure message customer dont have transactions in this account or account not found







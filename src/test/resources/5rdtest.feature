Feature: add transaction debit in account
    Scenario:add transaction debit in account
    Given the user is on accounts page
    When  the user enters valid id account
    And   hits search
    And   hits check button debit
    And   the user enters valid amount and description
    And   hits save operation
    Then  the user found the result in data base

  Scenario:add transaction debit in account failed
    Given the user is on accounts page
    When  the user enters valid id account
    And   hits search
    And   hits check button debit
    And   the user enters invalid amount and description
    And   hits save operation
    Then  the user should see a failure message

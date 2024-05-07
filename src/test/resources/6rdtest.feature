Feature: add transaction credit in account
  Scenario:add transaction credit in account
    Given the user is on accounts page1
    When  the user enters valid id account1
    And   hits search1
    And   hits check button credit
    And   the user enters valid amount and description1
    And   hits save operation1
    Then  the user found the result in data base1

  Scenario:add transaction credit in account failed
    Given the user is on accounts page1
    When  the user enters valid id account1
    And   hits search1
    And   hits check button credit
    And   the user enters invalid amount and description1
    And   hits save operation1
    Then  the user should see a failure message1

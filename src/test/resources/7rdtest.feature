Feature: add transaction for transfer  from an  account to another account of the same customer
  Scenario:add transaction transfer in account success (transfer from one account to another account in the same customer)
    Given the user is on accounts page2
    When  the user enters valid id account2
    And   hits search2
    And   hits check button transfer
    And   the user enters valid account destination amount and description
    And   hits save operation2
    Then  the user found the result in data base2

  Scenario:add transaction transfer in account fail(transfer from one account to another account in the same customer)
    Given the user is on accounts page2
    When  the user enters valid id account2
    And   hits search2
    And   hits check button transfer
    And   hits save operation2
    And   the user enters invalid account destination amount and description
    Then  the user should see a failure message2


Feature:  test the new customer page


  Scenario: the User should  create a new customer
    Given the user is on new customer page
    When the user enters valid name
    And  the user enters valid email
    And   hits save
    Then  the user should see a success message
    And  the user should see the result imported form the database

  Scenario: the User fails to create a new customer with invalid type name
    Given the user is on new customer page
    When  the user enters invalid name
    And   the user enters valid email
    And   hits save
    Then  the user should see a failure message with invalid name

  Scenario: the User fails to create a new customer with invalid type email
    Given the user is on new customer page
    When  the user enters valid name
    And   the user enters invalid email
    And   hits save
    Then  the user should see a failure message with invalid email



  Scenario: the User fails to create a new customer  with email that already exists
    Given the user is on new customer page
    When  the user enters valid name
    And   the user enters email already exists
    And   hits save
    Then  the user should see a failure message with email already exists


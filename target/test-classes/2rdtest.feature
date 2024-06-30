Feature:  test the new customer page


  Scenario: the User should  create a new customer
    Given the user on the web page
    When the user clicked on start
    And the user enters valid username
    And the user enters valid email
    And the user enters valid password
    And the user enters valid password confirmation
    And the user hits i agree
    And the user hits save
    And  the user hits spin
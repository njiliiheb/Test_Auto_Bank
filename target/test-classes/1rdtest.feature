
Feature: test login functionality
  Scenario: User should be able to login with valid credentials
    Given the user is on login page
    When  the user enters valid credentials
    And  hits submit
    Then the user should be logged in successfully


  Scenario: Failed login with incorrect username
    Given the user is on login page
    When the user enters incorrect username
    And the user enters valid password
    And hits submit
    Then the user should see a login failure message incorrect username

  Scenario: Failed login with incorrect password
    Given the user is on login page
    When the user enters valid username
    And the user enters incorrect password
    And hits submit
    Then the user should see a login failure message incorrect password





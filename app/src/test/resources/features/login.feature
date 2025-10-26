Feature: Login Validation

  Scenario: Validate behavior on the login page with invalid credentials
    Given the user is on the login page
    When the user submits invalid credentials

  Scenario: Validate behavior on the login page with valid credentials
    Given the user is on the login page
    When the user submits valid credentials
    Then the user should be redirected to the account page
Feature: Account Registration Form Validation

  Scenario: Validate behavior on the registration page with invalid data
    Given the user is on the registration page
    When the user submits invalid data
  
  Scenario: Validate behavior on the registration page with valid data
    Given the user is on the registration page
    When the user submits valid data
    Then the user should be redirected to the success page
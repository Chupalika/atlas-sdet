Feature: Account Registration Form Validation

  Scenario Outline: Validate behavior on the registration page
    Given the user is on the registration page
    When the user submits invalid data
    Then the correct error messages should be displayed
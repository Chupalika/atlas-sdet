Feature: Account Registration Form Validation

  Scenario Outline: Validate behavior on the registration page
    Given the user is on the registration page
    When the user submits the following invalid data:
      | fieldName | fieldLabel | value | validator | minLength | maxLength |
      | firstname | First Name |       | length    | 1         | 32        |
      | lastname  | Last Name  |       | length    | 1         | 32        |
    Then the correct error messages should be displayed
package org.example.pages.validators;

import org.example.pages.forms.GenericFormWrapper;
import org.openqa.selenium.By;

public class EmailValidator implements FieldValidator {
  public EmailValidator() {
  }

  @Override
  public void validate(String fieldName, String fieldLabel, GenericFormWrapper<?> form) {
    String[] invalidValues = new String[]{"invalid-email", "another-invalid-email@", "user@domain", "user@.com"};
    String[] validValues = new String[]{"valid@example.com"};

    for (String value : invalidValues) {
      form.fillOutField(fieldName, value).submit();
      By errorLocator = By.xpath("//div[@class='text-danger']");
      String expectedError = fieldLabel + " does not appear to be valid!";
      boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
        return element.getText().equals(expectedError);
      });
      if (!errorFound) {
        throw new AssertionError("Field '" + fieldName + "' expected error '" + expectedError + "'");
      }
    }

    for (String value : validValues) {
      form.fillOutField(fieldName, value).submit();
      By errorLocator = By.xpath("//div[@class='text-danger']");
      boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
        return element.getText().contains(fieldLabel);
      });
      if (errorFound) {
        // todo: add error message
        throw new AssertionError(String.format("Field '%s' with valid value '%s' displayed an error: %s", fieldName, value));
      }
    }
  }
}

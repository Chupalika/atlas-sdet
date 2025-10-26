package org.example.pages.validators;

import org.example.pages.forms.GenericFormWrapper;
import org.openqa.selenium.By;

public class LengthValidator implements FieldValidator {
  private int minLength;
  private int maxLength;

  public LengthValidator(int minLength, int maxLength) {
    this.minLength = minLength;
    this.maxLength = maxLength;
  }

  @Override
  public void validate(String fieldName, String fieldLabel, GenericFormWrapper<?> form) {
    String[] invalidValues = new String[]{"a".repeat(this.minLength - 1), "a".repeat(this.maxLength + 1)};
    String[] validValues = new String[]{"a".repeat((this.minLength + this.maxLength) / 2)};

    for (String value : invalidValues) {
      form.fillOutField(fieldName, value).submit();
      By errorLocator = By.xpath("//div[@class='text-danger']");
      String expectedError = fieldLabel + " must be between " + this.minLength + " and " + this.maxLength + " characters!";
      boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
        return element.getText().equals(expectedError);
      });
      if (!errorFound) {
        throw new AssertionError("' expected error '" + expectedError + "' not found");
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

package org.example.pages.validators;

import org.example.pages.forms.GenericFormWrapper;
import org.openqa.selenium.By;

public class LengthValidator implements FieldValidator {
  private String value;
  private int minLength;
  private int maxLength;

  public LengthValidator(String value, int minLength, int maxLength) {
    this.value = value;
    this.minLength = minLength;
    this.maxLength = maxLength;
  }

  @Override
  public void validate(String fieldName, String fieldLabel, GenericFormWrapper<?> form) {
    form.fillOutField(fieldName, this.value).submit();
    By errorLocator = By.xpath("//div[@class='text-danger']");
    String expectedError = fieldLabel + " must be between " + this.minLength + " and " + this.maxLength + " characters!";
    boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
      return element.getText().equals(expectedError);
    });
    if (!errorFound) {
      throw new AssertionError("' expected error '" + expectedError + "' not found");
    }
  }
}

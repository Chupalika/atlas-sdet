package org.example.pages.validators;

import org.example.pages.forms.GenericFormWrapper;
import org.openqa.selenium.By;

public class EmailValidator implements FieldValidator {
  private final String value;

  public EmailValidator(String value) {
    this.value = value;
  }

  @Override
  public void validate(String fieldName, String fieldLabel, GenericFormWrapper<?> form) {
    form.fillOutField(fieldName, this.value).submit();
    By errorLocator = By.xpath("//div[@class='text-danger']");
    String expectedError = fieldLabel + " does not appear to be valid!";
    boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
      return element.getText() == expectedError;
    });
    if (!errorFound) {
      throw new AssertionError("Field '" + fieldName + "' expected error '" + expectedError + "'");
    }
  }
}

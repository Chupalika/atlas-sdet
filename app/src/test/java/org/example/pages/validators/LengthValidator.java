package org.example.pages.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.example.pages.FormField;
import org.example.pages.forms.GenericFormWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LengthValidator implements FieldValidator {
  private int minLength;
  private int maxLength;

  public LengthValidator(int minLength, int maxLength) {
    this.minLength = minLength;
    this.maxLength = maxLength;
  }

  @Override
  public List<String> validateErrorMessages(String fieldName, GenericFormWrapper<?> form) {
    FormField field = form.fields.get(fieldName);
    By errorLocator = By.xpath(field.errorXpath());
    String[] invalidValues = new String[]{"a".repeat(this.minLength - 1), "a".repeat(this.maxLength + 1)};
    String[] validValues = new String[]{"a".repeat((this.minLength + this.maxLength) / 2)};

    List<String> errors = new ArrayList<>();
    for (String value : invalidValues) {
      form.fillOutField(fieldName, value).submit();
      String expectedError = String.format("%s must be between %d and %d characters!", field.label(), this.minLength, this.maxLength);
      boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
        return element.getText().equals(expectedError);
      });
      if (!errorFound) {
        errors.add(String.format("Field %s expected error message '%s'", fieldName, expectedError));
      }
    }

    for (String value : validValues) {
      form.fillOutField(fieldName, value).submit();
      List<String> errorMessages = form.getDriver().findElements(errorLocator).stream().map(WebElement::getText).collect(Collectors.toList());
      if (!errorMessages.isEmpty()) {
        errors.add(String.format("Field '%s' with valid value '%s' displayed an error: %s", fieldName, value, errorMessages.get(0)));
      }
    }

    return errors;
  }

  @Override
  public void fillWithValidData(String fieldName, GenericFormWrapper<?> form) {
    String validValue = "a".repeat(this.minLength);
    form.fillOutField(fieldName, validValue);
  }
}

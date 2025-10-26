package org.example.pages.validators;

import java.util.ArrayList;
import java.util.List;

import org.example.pages.FormField;
import org.example.pages.forms.GenericFormWrapper;
import org.openqa.selenium.By;

public class RequiredCheckboxValidator implements FieldValidator {
  @Override
  public List<String> validateErrorMessages(String fieldName, GenericFormWrapper<?> form) {
    FormField field = form.fields.get(fieldName);
    List<String> errors = new ArrayList<>();

    form.checkCheckbox(fieldName, false);
    By errorLocator = By.xpath(field.errorXpath());
    String expectedError = String.format("Warning: You must agree to the %s!", field.label());
    boolean errorFound = form.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
      return element.getText().equals(expectedError);
    });
    if (!errorFound) {
      errors.add(String.format("Field %s expected error message '%s'", fieldName, expectedError));
    }

    return errors;
  }

  @Override
  public void fillWithValidData(String fieldName, GenericFormWrapper<?> form) {
    form.checkCheckbox(fieldName, true);
  }
}

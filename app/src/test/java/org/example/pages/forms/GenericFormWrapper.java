package org.example.pages.forms;
import java.util.HashMap;
import java.util.Map;

import org.example.pages.FormField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GenericFormWrapper<F extends GenericFormWrapper<F>> {
  protected WebDriver driver;
  protected Map<String, FormField> fields = new HashMap<>();
  protected By submitButton;

  public GenericFormWrapper(WebDriver driver) {
    this.driver = driver;
  }

  public F fillOutField(String fieldName, String value) {
    FormField field = this.fields.get(fieldName);
    if (field == null) throw new IllegalArgumentException("Unknown field: " + fieldName);
    By locator = field.locator();
    driver.findElement(locator).clear();
    driver.findElement(locator).sendKeys(value != null ? value : "");
    return (F) this;
  }

  public F submit() {
    driver.findElement(this.submitButton).click();
    return (F) this;
  }

  public void validateFields() {
    for (String fieldName : this.fields.keySet()) {
      FormField field = this.fields.get(fieldName);
      for (var validator : field.validators()) {
        validator.validate(field.name(), field.label(), this);
      }
    }
  }

  public WebDriver getDriver() {
    return this.driver;
  }
}

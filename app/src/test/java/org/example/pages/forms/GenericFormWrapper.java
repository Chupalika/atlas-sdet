package org.example.pages.forms;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.pages.FormField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class GenericFormWrapper<F extends GenericFormWrapper<F>> {
  public WebDriver driver;
  public Map<String, FormField> fields = new HashMap<>();
  public By submitButtonLocator;

  public GenericFormWrapper(WebDriver driver) {
    this.driver = driver;
  }

  public F fillOutField(String fieldName, String value) {
    WebElement element = this.getFormElement(fieldName);
    element.clear();
    element.sendKeys(value != null ? value : "");
    return (F) this;
  }

  public F checkCheckbox(String fieldName, boolean checked) {
    WebElement element = this.getFormElement(fieldName);
    boolean isChecked = element.isSelected();
    if (isChecked != checked) {
      element.click();
    }
    return (F) this;
  }

  private WebElement getFormElement(String fieldName) {
    FormField field = this.fields.get(fieldName);
    if (field == null) {
      throw new IllegalArgumentException("Unknown field: " + fieldName);
    }
    By locator = By.xpath(field.xpath());
    return driver.findElement(locator);
  }

  public F submit() {
    driver.findElement(this.submitButtonLocator).click();
    this.waitForPostSubmit();
    return (F) this;
  }

  public abstract F waitForPostSubmit();

  public List<String> validateFieldsIncorrectly() {
    List<String> errors = new java.util.ArrayList<>();
    for (String fieldName : this.fields.keySet()) {
      FormField field = this.fields.get(fieldName);
      for (var validator : field.validators()) {
        errors.addAll(validator.validateErrorMessages(field.name(), this));
      }
    }
    return errors;
  }

  public F fillFieldsWithValidData() {
    for (String fieldName : this.fields.keySet()) {
      FormField field = this.fields.get(fieldName);
      for (var validator : field.validators()) {
        validator.fillWithValidData(field.name(), this);
      }
    }
    return (F) this;
  }

  public WebDriver getDriver() {
    return this.driver;
  }
}

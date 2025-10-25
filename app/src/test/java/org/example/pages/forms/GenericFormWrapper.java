package org.example.pages.forms;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GenericFormWrapper<F extends GenericFormWrapper<F>> {
  protected WebDriver driver;
  protected Map<String, By> fields = new HashMap<>();
  protected By submitButton;

  public GenericFormWrapper(WebDriver driver) {
    this.driver = driver;
  }

  public F fillOutField(String fieldName, String value) {
    By locator = this.fields.get(fieldName);
    if (locator == null) throw new IllegalArgumentException("Unknown field: " + fieldName);
    driver.findElement(locator).clear();
    driver.findElement(locator).sendKeys(value != null ? value : "");
    return (F) this;
  }

  public F submit() {
    driver.findElement(this.submitButton).click();
    return (F) this;
  }

  public WebDriver getDriver() {
    return this.driver;
  }
}

package org.example.pages.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationForm extends GenericFormWrapper<RegistrationForm> {
  public RegistrationForm(WebDriver driver) {
    super(driver);
    fields.put("firstname", By.name("firstname"));
    fields.put("lastname", By.name("lastname"));
    submitButton = By.xpath("//input[@type='submit']");
  }
}

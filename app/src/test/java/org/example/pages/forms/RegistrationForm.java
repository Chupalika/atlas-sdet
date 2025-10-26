package org.example.pages.forms;

import java.util.List;

import org.example.pages.FormField;
import org.example.pages.validators.LengthValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationForm extends GenericFormWrapper<RegistrationForm> {
  public RegistrationForm(WebDriver driver) {
    super(driver);
    this.fields.put(
      "firstname",
      new FormField(
        "firstname",
        "First Name",
        By.name("firstname"),
        List.of(new LengthValidator(1, 32))));
    this.fields.put(
      "lastname",
      new FormField(
        "lastname",
        "Last Name",
        By.name("lastname"),
        List.of(new LengthValidator(1, 32))));
    this.submitButton = By.xpath("//input[@type='submit']");
  }
}

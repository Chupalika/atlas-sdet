package org.example.pages.forms;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.example.pages.FormField;
import org.example.pages.validators.EmailValidator;
import org.example.pages.validators.LengthValidator;
import org.example.pages.validators.RequiredCheckboxValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationForm extends GenericFormWrapper<RegistrationForm> {
  public RegistrationForm(WebDriver driver) {
    super(driver);
    this.fields.put(
      "firstname",
      new FormField(
        "firstname",
        "First Name",
        "//input[@name='firstname']",
        "//input[@name='firstname']/following-sibling::div[@class='text-danger']",
        List.of(new LengthValidator(1, 32))));
    this.fields.put(
      "lastname",
      new FormField(
        "lastname",
        "Last Name",
        "//input[@name='lastname']",
        "//input[@name='lastname']/following-sibling::div[@class='text-danger']",
        List.of(new LengthValidator(1, 32))));
    this.fields.put(
      "email",
      new FormField(
        "email",
        "E-Mail Address",
        "//input[@name='email']",
        "//input[@name='email']/following-sibling::div[@class='text-danger']",
        List.of(new EmailValidator())));
    this.fields.put(
      "telephone",
      new FormField(
        "telephone",
        "Telephone",
        "//input[@name='telephone']",
        "//input[@name='telephone']/following-sibling::div[@class='text-danger']",
        List.of(new LengthValidator(3, 32))));
    this.fields.put(
      "password",
      new FormField(
        "password",
        "Password",
        "//input[@name='password']",
        "//input[@name='password']/following-sibling::div[@class='text-danger']",
        List.of(new LengthValidator(4, 20))));
    this.fields.put(
      "agree",
      new FormField(
        "agree",
        "Privacy Policy",
        "//input[@name='agree']",
        "//div[@class='alert alert-danger alert-dismissible']",
        List.of(new RequiredCheckboxValidator()))
    );
    this.submitButtonLocator = By.xpath("//input[@type='submit']");
  }

  @Override
  public List<String> validateFieldsIncorrectly() {
    List<String> errors = super.validateFieldsIncorrectly();

    // Special case for password confirmation
    this.fields.put("confirm",
      new FormField(
        "confirm",
        "Password confirmation",
        "//input[@name='confirm']",
        "//input[@name='confirm']/following-sibling::div[@class='text-danger']",
        List.of()));
    FormField passwordField = this.fields.get("password");
    FormField confirmField = this.fields.get("confirm");

    // Invalid case
    this.fillOutField(passwordField.name(), "test123")
        .fillOutField(confirmField.name(), "differentPassword")
        .submit();
    String expectedError = confirmField.label() + " does not match password!";
    By errorLocator = By.xpath(confirmField.errorXpath());
    boolean errorFound = this.getDriver().findElements(errorLocator).stream().anyMatch(element -> {
      return element.getText().equals(expectedError);
    });
    if (!errorFound) {
      errors.add(String.format("Field '%s' expected error '%s'", "confirm", expectedError));
    }

    // Valid case
    this.fillOutField("password", "test123")
        .fillOutField("confirm", "test123")
        .submit();
    List<String> errorMessages = this.getDriver().findElements(errorLocator).stream().map(WebElement::getText).collect(Collectors.toList());
    if (!errorMessages.isEmpty()) {
      errors.add(String.format("Field '%s' with valid values displayed an error: %s", "confirm", errorMessages.get(0)));
    }
    return errors;
  }

  @Override
  public RegistrationForm waitForPostSubmit() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.or(
      ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='text-danger']")),
      ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert alert-danger alert-dismissible']")),
      ExpectedConditions.urlContains("success")));
    return this;
  }
}
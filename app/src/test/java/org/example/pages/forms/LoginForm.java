package org.example.pages.forms;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.example.pages.FormField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginForm extends GenericFormWrapper<LoginForm> {
  public LoginForm(WebDriver driver) {
    super(driver);
    this.fields.put(
      "email",
      new FormField(
        "email",
        "E-mail Address",
        "//input[@name='email']",
        "",
        List.of()));
    this.fields.put(
      "password",
      new FormField(
        "password",
        "Password",
        "//input[@name='password']",
        "",
        List.of()));
    this.submitButtonLocator = By.xpath("//input[@type='submit']");
  }

  public List<String> invalidateLogin() {
    By errorMessageLocator = By.cssSelector(".alert.alert-danger.alert-dismissible");
    this.fillOutField("email", "invalid@example.com")
        .fillOutField("password", "wrongpassword")
        .submit();
    List<String> errorMessages = this.getDriver().findElements(errorMessageLocator).stream().map(WebElement::getText).collect(Collectors.toList());
    String expectedError = "No match for E-Mail Address and/or Password.";
    if (errorMessages.isEmpty()) {
      return List.of("Login with invalid credentials displayed no error messages.");
    } else if (!errorMessages.get(0).contains(expectedError)) {
      return List.of(String.format("Login with invalid credentials expected error message '%s', got '%s'", expectedError, errorMessages.get(0)));
    } else {
      return List.of();
    }
  }

  @Override
  public LoginForm waitForPostSubmit() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.or(
      ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert alert-danger alert-dismissible']")),
      ExpectedConditions.urlContains("account/account")));
    return this;
  }
}

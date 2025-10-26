package org.example.stepdefinitions;

import java.util.List;

import org.example.pages.forms.RegistrationForm;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class RegistrationSteps {
  private WebDriver driver = new ChromeDriver();
  private RegistrationForm form = new RegistrationForm(driver);

  @Given("the user is on the registration page")
  public void openRegistrationPage() {
    driver.get("https://tutorialsninja.com/demo/index.php?route=account/register");
  }

  @When("the user submits invalid data")
  public void submitInvalidData() {
    List<String> errors = form.validateFieldsIncorrectly();
    if (!errors.isEmpty()) {
      Assertions.fail(String.join("\n", errors));
    }
  }

  @When("the user submits valid data")
  public void submitValidData() {
    List<String> errors = form.validateFieldsCorrectly();
    if (!errors.isEmpty()) {
      Assertions.fail(String.join("\n", errors));
    }
  }

  @Then("the correct error messages should be displayed")
  public void placeholder() {}

  @Then("the user should be redirected to the success page")
  public void placeholder2() {}
}

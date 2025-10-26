package org.example.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.example.pages.forms.RegistrationForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrationSteps {
  private WebDriver driver = new ChromeDriver();
  private RegistrationForm form = new RegistrationForm(driver);

  @Given("the user is on the registration page")
  public void openRegistrationPage() {
    driver.get("https://tutorialsninja.com/demo/index.php?route=account/register");
  }

  @When("the user submits invalid data")
  public void submitInvalidRegistrationData() {
    List<String> errors = form.validateFieldsIncorrectly();
    assertTrue(errors.isEmpty(), String.join("\n", errors));
  }

  @When("the user submits valid data")
  public void submitValidRegistrationData() {
    form.fillFieldsWithValidData().submit();
  }

  @Then("the user should be redirected to the success page")
  public void redirectToSuccessPage() {
    assertTrue(this.driver.getCurrentUrl().endsWith("account/success"), "Expected to be redirected to success page.");
  }

  @After
  public void tearDown() {
    driver.quit();
  }
}

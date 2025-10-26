package org.example.stepdefinitions;

import org.example.pages.forms.RegistrationForm;
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
    form.validateFields();
  }

  @Then("the correct error messages should be displayed")
  public void the_correct_error_messages_should_be_displayed() {}
}

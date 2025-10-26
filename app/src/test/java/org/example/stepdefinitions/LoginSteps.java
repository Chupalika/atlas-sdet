package org.example.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.example.pages.forms.LoginForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
  private WebDriver driver = new ChromeDriver();
  private LoginForm form = new LoginForm(driver);

  @Given("the user is on the login page")
  public void openLoginPage() {
    driver.get("https://tutorialsninja.com/demo/index.php?route=account/login");
  }

  @When("the user submits invalid credentials")
  public void submitInvalidLoginCredentials() {
    List<String> errors = form.invalidateLogin();
    assertTrue(errors.isEmpty(), String.join("\n", errors));
  }

  @When("the user submits valid credentials")
  public void submitValidLoginCredentials() {
    form.fillOutField("email", "asdfxyz@asdfxyz.com")
        .fillOutField("password", "a".repeat(4))
        .submit();
  }

  @Then("the user should be redirected to the account page")
  public void redirectToAccountPage() {
    assertTrue(this.driver.getCurrentUrl().endsWith("account/account"), "Expected to be redirected to account page");
  }

  @After
  public void tearDown() {
    driver.quit();
  }
}

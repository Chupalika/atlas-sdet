package org.example.stepdefinitions;

import java.util.List;
import java.util.Map;

import org.example.pages.forms.RegistrationForm;
import org.example.pages.validators.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.datatable.DataTable;
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

  @When("the user submits the following invalid data:")
  public void submitInvalidData(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row: rows) {
      String fieldName = row.get("fieldName");
      String fieldLabel = row.get("fieldLabel");
      String value = row.get("value");
      String validatorType = row.get("validator");
      int minLength = Integer.parseInt(row.get("minLength"));
      int maxLength = Integer.parseInt(row.get("maxLength"));

      FieldValidator validator = switch(validatorType.toLowerCase()) {
        case "length" -> {
          yield new LengthValidator(value, minLength, maxLength);
        }
        default -> {
          throw new IllegalArgumentException("Unknown validator: " + validatorType);
        }
      };
      validator.validate(fieldName, fieldLabel, this.form);
    }
  }

  @Then("the correct error messages should be displayed")
  public void the_correct_error_messages_should_be_displayed() {}
}

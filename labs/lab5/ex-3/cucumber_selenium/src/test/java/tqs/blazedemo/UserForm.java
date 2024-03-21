package tqs.blazedemo;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebElement;

public class UserForm {

    private WebDriver driver;
    
    @When("I navigate to the {string} page")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @And("I enter my {string} as {string}")
    public void iEnterValueOnEntry(String entry, String value) {
        driver.findElement(By.id(entry)).click();
        driver.findElement(By.id(entry)).sendKeys(value);
    }

    @And("I select my card type as {string}")
    public void iEnteCardType(String card) {
        driver.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = '" + card + "']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".control-group:nth-child(8)")).click();
    }

    @Then("I click purchase flight")
    public void iPressEnter() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should see the message {string} and the value {string}")
    public void iShouldSeeBoth(String result0, String result1) {
        try {
            driver.findElement(
                    By.xpath("//*[contains(text(), '" + result0 + "')]"));
        } catch (NoSuchElementException e) {
            driver.quit();
            throw new AssertionError(
                    "\"" + result0 + "\" not available in results");
        } 

        try {
            driver.findElement(
                    By.xpath("//*[contains(text(), '" + result1 + "')]"));
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result1 + "\" not available in results");
        } finally {
            driver.quit();
        }
    }

}
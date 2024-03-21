package tqs.blazedemo;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightOption {

    private WebDriver driver;

    @When("I navigate to {string} at page {string}")
    public void iNavigateTo(String url, String page) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url + page);
    }

    @And("I click on option number {int}")
    public void iChosenAnOption(int optionIdx) {
        driver.findElement(By.cssSelector("tr:nth-child(" + optionIdx + ") .btn")).click();
    }

    @Then("I should see the message {string} and {string}")
    public void iShouldSeeTwo(String result0, String result1) {
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
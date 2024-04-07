package hw1.roadroam.frontendTests;


import hw1.roadroam.frontendTests.webpages.HomePage;
import hw1.roadroam.frontendTests.webpages.ReceiptPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.time.Duration;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


@ExtendWith(SeleniumJupiter.class)
public class ReceiptPageTests {


    private WebDriver driver;
    private ReceiptPage receiptPage;




    @Given("the user accessed the receipt page with ticket value {int}")
    public void userEntersPurchasePage(Integer ticket) {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        receiptPage = new ReceiptPage(driver, ticket);
    }


    @When("the user sees the final value as {string}")
    public void userSeesTheFinalValue(String finalPrice) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String headerText = receiptPage.getTotalCost();
        assertEquals("55.12", headerText);
    }


    @And("clicks to go to the home page")
    public void userGoesBackToHomePage() {
        receiptPage.clickOnGoToHomePageButton();
    }


    @Then("the user should see the ticket listed with price {string}")
    public void userSeesTheTicketInTheList(String finalPrice) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String headerText = receiptPage.getTotalCostFromList();
        assertEquals(" 55.12 USD ", headerText);

        driver.close();
    }
}
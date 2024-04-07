package hw1.roadroam.frontendTests;


import hw1.roadroam.frontendTests.webpages.TicketDetails;

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
public class DetailsPageTests {


    private WebDriver driver;
    private TicketDetails ticketDetailsPage;




    @Given("the user accessed the purchase page with trip value {int} and currency as {string}")
    public void userEntersPurchasePage(Integer trip, String currency) {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        ticketDetailsPage = new TicketDetails(driver, trip, currency);
    }




    @When("the user inputs his first name as {string}")
    public void userInputsFname(String fname)  {
        ticketDetailsPage.setFname(fname);
    }

    @And("the user inputs his last name as {string}")
    public void userInputsLname(String value)  {
        ticketDetailsPage.setLname(value);
    }

    @And("the user inputs his phone number as {string}")
    public void userInputs(String value)  {
        ticketDetailsPage.setPhone(value);
    }

    @And("the user inputs his email as {string}")
    public void userInputsEmail(String value)  {
        ticketDetailsPage.setEmail(value);
    }

    @And("the user selects the credit card as index {int}")
    public void userSelectsCCard(Integer value)  {
        ticketDetailsPage.setCreditCard(value);
    }

    @And("the user inputs the number of people as {string}")
    public void userInputsNPeople(String value)  {
        ticketDetailsPage.setNumPeople(value);
    }

    @And("the user selects the seat as index {int}")
    public void userSelectsSeat(Integer value)  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        ticketDetailsPage.setSeat(value);
    }

    @And("the user presses the purchase button")
    public void userPurchases()  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        ticketDetailsPage.clickPurchaseTicketButton();
    }

    @Then("the user should go to the confirmation page")
    public void userGoesToTrips() {

        String headerText = ticketDetailsPage.getTotalCost();
        assertEquals("55.12", headerText);

        driver.close();
    }
}
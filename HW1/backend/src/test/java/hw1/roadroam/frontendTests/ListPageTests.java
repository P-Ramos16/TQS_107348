package hw1.roadroam.frontendTests;

import hw1.roadroam.frontendTests.webpages.SelectTrip;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


@ExtendWith(SeleniumJupiter.class)
public class ListPageTests {


    private WebDriver driver;
    private SelectTrip selectTripPage;




    @Given("the user accessed the trips list with route value {int}")
    public void userEntersTripListPage(Integer route) {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        selectTripPage = new SelectTrip(driver, route);
    }





    @When("the user selects the trip number {int}")
    public void userSelectsTrip(Integer originCity)  {
        selectTripPage.selectTrip(originCity);
    }

    @Then("the user should go to the purchase page")
    public void userGoesToDetails() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String headerText = selectTripPage.getCurrentPrice();
        assertEquals("12.72 USD", headerText);

        driver.close();
    }
}
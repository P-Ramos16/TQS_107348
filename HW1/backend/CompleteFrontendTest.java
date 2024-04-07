package hw1.roadroam.frontendTests;

import hw1.roadroam.frontendTests.webpages.SelectTrip;
import hw1.roadroam.frontendTests.webpages.TicketDetails;
import hw1.roadroam.frontendTests.webpages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class CompleteFrontendTest {
   
    static WebDriver driver;

    @BeforeAll
    static void setup(){
        //use FF Driver
        driver = new FirefoxDriver();
        driver.manage().timeouts();
    }

    /* @Test
    static void purchaseFlight() {
        //Create object of HomePage Class
        HomePage home = new HomePage(driver);
        home.selectOnOriginSelectBox(2);
        home.selectOnDestinationSelectBox(2);
        home.clickOnFindFlightsButton();

        //Create object of DeveloperPortalPage
        ChooseFlightPage flightPage = new ChooseFlightPage(driver);

        //Check if page is opened
        assertThat(flightPage.isPageOpened(), is(true));

        //Click on Join Toptal
        flightPage.clickChooseFlightButton();

        //Create object of DeveloperFlightForm
        FlightForm flightForm = new FlightForm(driver);

        //Check if page is opened
        assertThat(flightForm.isPageOpened(), is(true));

        //Fill up data
        flightForm.setName("Josefino Calças");
        flightForm.setAddress("123 Main ST.");
        flightForm.setCity("Lisbon");
        flightForm.setState("Lisbon");
        flightForm.setZipCode("1500-409");
        flightForm.setCardType(1);
        flightForm.setCreditCard("123123132");
        flightForm.setCardMonth("11");
        flightForm.setCardYear("2018");
        flightForm.setCardName("JosefinoCalças");

        //  Assert the final cost of the flight
        assertThat(flightForm.assertTotalCost(), is(true));

        //Click on join
        flightForm.clickPurchaseFlightButton();  
        assertTrue(true);
    }

    @AfterAll
    static void close(){
        driver.close();
    } */
}

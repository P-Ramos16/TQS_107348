package hw1.roadroam.frontendTests;


import hw1.roadroam.frontendTests.webpages.SelectTrip;
import hw1.roadroam.frontendTests.webpages.TicketDetails;
import hw1.roadroam.frontendTests.webpages.HomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


@ExtendWith(SeleniumJupiter.class)
public class CucumberFrontendTests {


    private WebDriver driver;
    private HomePage homePage;
    private SelectTrip selectTripPage;
    private TicketDetails ticketDetailsPage;


    @Given("the user entered in the website")
    public void the_user_entered_in_the_website() {
        driver = new ChromeDriver();
        //maximize window
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        buyTicketPage = new BuyTicketPage(driver);
        listTripsPage = new ListTripsPage(driver);
        homePage.open();
    }

    @When("the user searches for trips")
    public void the_user_searches_for_trips()  {
        homePage.clickSeeTripsButton() ;
    }

    @And("selects the first trip")
    public void selects_the_first_trip() throws InterruptedException  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        listTripsPage.selectFirstTrip();
    }

    @And("selects the seat {int}")
    public void selects_the_seat(Integer seat) {
        buyTicketPage.selectSeat(seat);
    }

    @And("the seat number {int} is not taken yet")
    public void the_seat_number_is_not_taken_yet(Integer seat) {
        buyTicketPage.checkSeatNotTaken(seat);
    }

    @And("the user inputs his information")
    public void the_user_inputs_his_information() {

        buyTicketPage.enterName("Zé Manel");
        buyTicketPage.enterPhone("912345678");
        buyTicketPage.enterEmail("zemanel@gmail.com");


    }

    @And("the user clicks on the buy button")
    public void the_user_clicks_on_the_buy_button() {
        buyTicketPage.clickBuyTicketButton();
    }

    @Then("the user should receive a confirmation message")
    public void the_user_should_receive_a_confirmation_message() {
      int size = buyTicketPage.getConfirmationMessage();
      assert(size > 0);
    }

    @And("the seat number {int} is already taken")
    public void the_seat_is_already_taken(Integer seat) {
        buyTicketPage.checkSeatTaken(seat);
    }

    @Then("the buy button should say {string}")
    public void the_button_should_say_seat_taken(String sitTakenString) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        assertEquals(sitTakenString, buyTicketPage.getSeatTakenMessage());
    }






    
}
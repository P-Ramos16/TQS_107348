package hw1.roadroam.frontendTests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectTrip {
    private WebDriver driver;

    @FindBy(xpath = "/html/body/div[2]/h3")
    private WebElement heading;

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[4]/td[1]/input")
    private WebElement chooseFlightButton;

    //Constructor
    public SelectTrip (WebDriver ndriver){
        driver=ndriver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    //We will use this boolean for assertion. To check if page is opened
    public boolean isPageOpened(){
        return heading.getText().toString().contains("Flights from Boston to London:");
    }

    public void clickChooseFlightButton(){
        chooseFlightButton.click();
    }
}

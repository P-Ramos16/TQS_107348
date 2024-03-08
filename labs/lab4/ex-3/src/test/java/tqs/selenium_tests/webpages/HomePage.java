package tqs.selenium_tests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL="https://blazedemo.com/";

    //Locators
    @FindBy(xpath = "/html/body/div[3]/form/select[1]")
    private WebElement departureSelectBox;

    @FindBy(xpath = "/html/body/div[3]/form/select[2]")
    private WebElement destinationSelectBox;

    @FindBy(xpath = "/html/body/div[3]/form/div/input")
    private WebElement findFlightsButton;

    //Constructor
    public HomePage(WebDriver ndriver){
        driver=ndriver;
        driver.get(PAGE_URL);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void clickOnFindFlightsButton(){
        findFlightsButton.click();
    }

    public void selectOnDepartureSelectBox(int index){
        Select drop = new Select(departureSelectBox);
        drop.selectByIndex(index);  
    }

    public void selectOnDestinationSelectBox(int index){
        Select drop = new Select(destinationSelectBox);
        drop.selectByIndex(index);  
    }
}

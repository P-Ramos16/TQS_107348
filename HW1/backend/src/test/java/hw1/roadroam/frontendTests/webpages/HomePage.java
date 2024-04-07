package hw1.roadroam.frontendTests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL="file:///home/frostywolf/Documents/GitReps/TQS_107348/HW1/frontend/src/index.html";
    //private static String PAGE_URL="https://localhost:3000";

    //Locators
    @FindBy(xpath = "//*[@id=\"origin\"]")
    private WebElement originSelectBox;

    @FindBy(xpath = "//*[@id=\"destination\"]")
    private WebElement destinationSelectBox;

    @FindBy(xpath = "/html/body/div[3]/form/div/input")
    private WebElement findTripsButton;

    //Constructor
    public HomePage(WebDriver ndriver){
        driver=ndriver;
        driver.get(PAGE_URL);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void clickOnFindFlightsButton(){
        findTripsButton.click();
    }

    public void selectOnOriginSelectBox(int index){
        Select drop = new Select(originSelectBox);
        drop.selectByIndex(index);  
    }

    public void selectOnDestinationSelectBox(int index){
        Select drop = new Select(destinationSelectBox);
        drop.selectByIndex(index);  
    }
}

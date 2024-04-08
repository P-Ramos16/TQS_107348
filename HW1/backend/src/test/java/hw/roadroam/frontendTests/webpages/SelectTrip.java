package hw.roadroam.frontendTests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;

public class SelectTrip {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL="file:///home/frostywolf/Documents/GitReps/TQS_107348/HW1/frontend/src/selectpage.html";
    //private static String PAGE_URL="https://localhost:3000";

    @FindBy(xpath = "/html/body/div[3]/table/tbody/tr[2]/td[8]/button")
    private WebElement chooseTripButton;

    //Constructor
    public SelectTrip(WebDriver ndriver, Integer route){
        driver=ndriver;
        driver.get(PAGE_URL + "?route=" + route);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void selectTrip(Integer tripNumber){
        chooseTripButton.click();
    }

    public String getCurrentPrice(){
        WebElement currentPrice = WaitForElement.fluentWait(driver, By.id("finalprice"));
        return currentPrice.getText();  
    }


    
}

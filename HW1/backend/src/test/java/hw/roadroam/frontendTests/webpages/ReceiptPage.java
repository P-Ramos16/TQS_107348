package hw.roadroam.frontendTests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;

import java.time.Duration;
import java.lang.Thread;

public class ReceiptPage {
    private WebDriver driver;

    //Page URL
    //private static String PAGE_URL="file:///home/frostywolf/Documents/GitReps/TQS_107348/HW1/frontend/src/receipt.html";
    private static String PAGE_URL="https://localhost:3000/receipt.html";

    //Locators

    //Constructor
    public ReceiptPage(WebDriver ndriver, Integer ticket){
        driver=ndriver;
        driver.get(PAGE_URL + "?ticket=" + ticket);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public String getTotalCost() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement finalPrice = driver.findElement(By.id("ticket_price"));

        return finalPrice.getText();
    }

    public void clickOnGoToHomePageButton(){
        WebElement seat = driver.findElement(By.id("returnBtn"));
        seat.click();
    }

    public String getTotalCostFromList(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement finalListPrice = driver.findElement(By.xpath("/html/body/div[4]/div/table/tbody/tr[1]/td[4]"));

        return finalListPrice.getText();
    }
}

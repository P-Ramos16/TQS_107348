package hw.roadroam.frontendTests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.lang.Thread;

public class TicketDetails {
    private WebDriver driver;

    //Page URL
    //private static String PAGE_URL="file:///home/frostywolf/Documents/GitReps/TQS_107348/HW1/frontend/src/purchase.html";
    private static String PAGE_URL="https://localhost:3000/purchase.html";

    @FindBy(id = "fname")
    WebElement fname;

    @FindBy(id = "lname")
    WebElement lname;

    @FindBy(id = "phone")
    WebElement phone;

    @FindBy(id="email")
    WebElement email;

    @FindBy(id = "creditcard")
    WebElement creditcard;

    @FindBy(id = "npeople")
    WebElement npeople;

    @FindBy(id = "purchasebutton")
    private WebElement purchaseTicketButton;

    //Constructor
    public TicketDetails(WebDriver ndriver, Integer trip, String currency){
        driver=ndriver;
        driver.get(PAGE_URL + "?trip=" + trip + "&currency=" + currency);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void setFname(String name){
        fname.clear();
        fname.sendKeys(name);
    }

    public void setLname(String name){
        lname.clear();
        lname.sendKeys(name);
    }
    
    public void setPhone(String ph){
        phone.clear();
        phone.sendKeys(ph);
    }
    
    public void setEmail(String mail){
        email.clear();
        email.sendKeys(mail);
    }

    public void setCreditCard(int index){
        Select drop = new Select(creditcard);
        drop.selectByIndex(index); 
    }
    
    public void setNumPeople(String num){
    }

    public void setSeat(int index) {
        WebElement seat = driver.findElement(By.id("seat" + index));
        seat.click();
    }

    public void clickPurchaseTicketButton(){
        purchaseTicketButton.click();
        //Wait for the alert to be displayed
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public String getTotalCost(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement finalPrice = driver.findElement(By.id("ticket_price"));

        return finalPrice.getText();
    }
}

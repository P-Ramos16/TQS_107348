package tqs.selenium_tests.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class FlightForm {
    private WebDriver driver;

    @FindBy(tagName = "h2")
    WebElement heading;

    @FindBy(id = "inputName")
    WebElement name;

    @FindBy(id = "address")
    WebElement address;

    @FindBy(id="city")
    WebElement city;

    @FindBy(id = "state")
    WebElement state;

    @FindBy(id = "zipCode")
    WebElement zipcode;

    @FindBy(id = "cardType")
    WebElement cardtype;

    @FindBy(id = "creditCardNumber")
    WebElement creditcard;

    @FindBy(id = "creditCardMonth")
    WebElement cardmonth;

    @FindBy(id = "creditCardYear")
    WebElement cardyear;

    @FindBy(id = "nameOnCard")
    WebElement cardname;

    @FindBy(xpath = "/html/body/div[2]/form/div[11]/div/input")
    private WebElement purchaseFlightButton;

    @FindBy(xpath = "/html/body/div[2]/p[5]/em")
    private WebElement totalCost;

    //Constructor
    public FlightForm(WebDriver ndriver){
        driver=ndriver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void setName(String newname){
        name.clear();
        name.sendKeys(newname);
    }

    public void setAddress(String newaddress){
        address.clear();
        address.sendKeys(newaddress);
    }

    public void setCity(String newcity){
        city.clear();
        city.sendKeys(newcity);
    }

    public void setState(String newstate){
        state.clear();
        state.sendKeys(newstate);
    }

    public void setZipCode(String newzipcode){
        zipcode.clear();
        zipcode.sendKeys(newzipcode);
    }

    public void setCardType(int index){
        Select drop = new Select(cardtype);
        drop.selectByIndex(index); 
    }

    public void setCreditCard(String newcreditcard){
        creditcard.clear();
        creditcard.sendKeys(newcreditcard);
    }

    public void setCardMonth(String newcardmonth){
        cardmonth.clear();
        cardmonth.sendKeys(newcardmonth);
    }

    public void setCardYear(String newcardyear){
        cardyear.clear();
        cardyear.sendKeys(newcardyear);
    }

    public void setCardName(String newcardname){
        cardname.clear();
        cardname.sendKeys(newcardname);
    }

    public void clickPurchaseFlightButton(){
        purchaseFlightButton.click();
    }

    public boolean isPageOpened(){
        //Assertion
        return heading.getText().toString().contains("Your flight from TLV to SFO has been reserved.");
    }

    public boolean assertTotalCost(){
        //Assertion
        return totalCost.getText().toString().contains("914.76");
    }
}

package hw1.roadroam.frontendTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndexPage {

    private final WebDriver driver;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){ 
        driver.get("http://localhost:5173/");
    }

    public void clickSeeTripsButton() {
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
    }


}
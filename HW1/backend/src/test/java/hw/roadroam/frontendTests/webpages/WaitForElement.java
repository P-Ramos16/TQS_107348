package hw.roadroam.frontendTests.webpages;


import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.By;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;


public class WaitForElement {
    public static WebElement fluentWait(WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
    
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
    
        return  foo;
    };
}

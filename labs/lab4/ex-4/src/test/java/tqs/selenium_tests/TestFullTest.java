package tqs.selenium_tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;

import java.util.Map;

import io.github.bonigarcia.seljup.EnabledIfDockerAvailable;
import static io.github.bonigarcia.seljup.BrowserType.EDGE;
import io.github.bonigarcia.seljup.DockerBrowser;

@EnabledIfDockerAvailable
@ExtendWith(SeleniumJupiter.class)
public class TestFullTest {


    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;
    private Map<String, Object> vars;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.edgedriver().setup();
    }

    @Test
    public void testFull(@DockerBrowser(type = EDGE) WebDriver driver) {
        driver.get("https://blazedemo.com/");
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Boston']")).click();
        }
        {
            WebElement element = driver.findElement(By.name("fromPort"));
            String value = element.getAttribute("value");
            String locator = String.format("option[@value='%s']", value);
            String selectedText = element.findElement(By.xpath(locator)).getText();
            assertThat(selectedText, is("Boston"));
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(3)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'London']")).click();
        }
        {
            WebElement element = driver.findElement(By.name("toPort"));
            String value = element.getAttribute("value");
            String locator = String.format("option[@value='%s']", value);
            String selectedText = element.findElement(By.xpath(locator)).getText();
            assertThat(selectedText, is("London"));
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(4)")).getText(), is("Virgin America"));
        driver.findElement(By.cssSelector("tr:nth-child(4) .btn")).click();
        assertThat(driver.findElement(By.cssSelector("em")).getText(), is("914.76"));
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("Josefino Calças");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("123 Main ST.");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("Lisbon");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("Lisbon");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("1500-409");
        driver.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".control-group:nth-child(8)")).click();
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("123123132");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("JosefinoCalças");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(2)")).getText(), is("555 USD"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(2)")).getText(), is("xxxxxxxxxxxx1111"));
    }
}
package tqs.selenium_tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;

@ExtendWith(SeleniumJupiter.class)
class FirefoxInjectionTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();   
    }

    @Test
    void test(FirefoxDriver driver) {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }


    @AfterEach
    void teardown() {
        driver.quit();
    }

}
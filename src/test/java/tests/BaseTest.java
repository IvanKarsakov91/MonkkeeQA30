package tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Configuration.browser = ConfigReader.get("browser");        // chrome / firefox
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000;
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless")); // true / false
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Автоматически закрывается при завершении теста, можно добавить очистку:
        com.codeborne.selenide.Selenide.closeWebDriver();
    }
}

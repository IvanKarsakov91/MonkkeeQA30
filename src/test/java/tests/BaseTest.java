package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import utils.ConfigReader;

import java.util.HashMap;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected final LoginPage loginPage = new LoginPage();

    protected final User defaultUser = new User(
            ConfigReader.get("user"),
            ConfigReader.get("password"),
            ConfigReader.get("passwordConfirmation"),
            null
    );

    protected final String startUrl = ConfigReader.get("startUrl");
    protected final String loginUrl = ConfigReader.get("loginUrl");
    protected final String registrationUrl = ConfigReader.get("registrationUrl");
    protected final String passwordReminderUrl = ConfigReader.get("passwordReminderUrl");
    protected final String languageUrl = ConfigReader.get("languageUrl");

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    @Step("Настройка браузера перед тестом")
    public void setUpBeforeTest(@Optional("chrome") String browserName) {
        configureBrowser(browserName);
    }

    @Step("Конфигурация браузера: {browserName}")
    private void configureBrowser(String browserName) {
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", chromePrefs);

            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1280,800");
            options.addArguments("--no-first-run");

            if (headless) {
                options.addArguments("--headless=new");
            }

            Configuration.browserCapabilities = options;
        }

        else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--inPrivate");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1280,800");

            if (headless) {
                options.addArguments("--headless=new");
            }

            Configuration.browserCapabilities = options;
        }

        else {
            throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserName);
        }

        Configuration.browser = browserName;
        Configuration.headless = headless;
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 6000;
        Configuration.browserSize = "1280x800";

        log.info("Браузер настроен: {}, headless: {}, размер: {}", browserName, headless, Configuration.browserSize);
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info("Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}



package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
            null // passwordHint можно добавить в config.properties
    );

    protected final String startUrl = ConfigReader.get("startUrl");
    protected final String loginUrl = ConfigReader.get("loginUrl");
    protected final String registrationUrl = ConfigReader.get("registrationUrl");
    protected final String passwordReminderUrl = ConfigReader.get("passwordReminderUrl");
    protected final String languageUrl = ConfigReader.get("languageUrl");

    @BeforeMethod(alwaysRun = true)
    @Step("Настройка браузера перед тестом")
    public void setUpBeforeTest() {
        configureBrowser();
    }

    @Step("Настройка браузера")
    private void configureBrowser() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);

        options.addArguments("--incognito");                       // включённый режим инкогнито
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--window-size=1280,800");

        Configuration.browser = ConfigReader.get("browser");       // chrome или твой кастомный драйвер
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 6000;
        Configuration.browserSize = "1280x800";
        Configuration.browserCapabilities = options;

        log.info("Браузер: {} запущен в режиме инкогнито, headless: {}", Configuration.browser, Configuration.headless);
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info("Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}





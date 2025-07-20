package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import pages.LoginPage;

import java.util.HashMap;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected LoginPage loginPage = new LoginPage();

    protected final User defaultUser = new User(
            "ivankarsakov91@gmail.com",
            "karsakov91",
            "karsakov91",
            "это мой тестовый пароль"
    );

    @BeforeMethod(alwaysRun = true)
    @Step("Настройка окружения перед тестом")
    public void setupAndLogin() {
        configureBrowser();
        loginPage.openLoginPage(); // внутри происходит инициализация WebDriver и очистка сессии
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());
        log.info("Авторизация выполнена под: {}", defaultUser.getEmail());
    }

    @Step("Настройка браузера")
    private void configureBrowser() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        Configuration.browser = "utils.CustomChromeDriver";
        Configuration.headless = false;
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 6000;
        Configuration.browserSize = "1280x800";
        Configuration.browserCapabilities = options;
        log.info("Браузер: Chrome визуально запущен");
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info("Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}


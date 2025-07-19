package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import pages.LoginPage;

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
        Configuration.browser = "utils.CustomChromeDriver";
        Configuration.headless = false;
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 6000;
        Configuration.browserSize = "1280x800";
        log.info("Браузер: Chrome визуально запущен");
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info("Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}


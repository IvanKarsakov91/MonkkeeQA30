package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.ConfigReader;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected LoginPage loginPage = new LoginPage();

    @BeforeMethod(alwaysRun = true)
    @Step("Настройка окружения перед тестом")
    public void setupAndMaybeLogin() {
        configureBrowser();

        boolean requireLogin = Boolean.parseBoolean(ConfigReader.get("requireLogin"));
        if (requireLogin) {
            log.info("Выполняем логин перед тестом");
            String email = ConfigReader.get("user");
            String password = ConfigReader.get("password");

            loginPage.openLoginPage();
            loginPage.login(email, password);

            log.info("Авторизация успешна под: {}", email);
        } else {
            log.info("Авторизация отключена для данного теста");
        }
    }

    @Step("Настройка кастомного драйвера браузера")
    private void configureBrowser() {
        Configuration.browser = "utils.CustomChromeDriver";
        Configuration.timeout = 6000;
        log.info("Браузер: CustomChromeDriver");
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info("Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}




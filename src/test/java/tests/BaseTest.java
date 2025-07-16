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

    @Parameters({"browser", "requireLogin"})
    @BeforeMethod(alwaysRun = true)
    @Step("Настройка окружения перед тестом")
    public void setupAndMaybeLogin(
            @Optional String browserParam,
            @Optional("true") String requireLoginParam
    ) {
        configureBrowser(browserParam);

        boolean requireLogin = Boolean.parseBoolean(requireLoginParam);
        if (requireLogin) {
            log.info(" Выполняем логин перед тестом");
            String email = ConfigReader.get("user");
            String password = ConfigReader.get("password");

            // Открываем страницу логина, чтобы инициализировать драйвер и загрузить страницу
            loginPage.openLoginPage();

            // Далее логинимся
            loginPage.login(email, password);

            log.info(" Авторизация успешна под: {}", email);
        } else {
            log.info(" Авторизация отключена для данного теста");
        }
    }

    @Step("Настройка браузера: {browserParam}")
    private void configureBrowser(String browserParam) {
        String browser = (browserParam != null && !browserParam.isBlank())
                ? browserParam
                : ConfigReader.get("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
            case "firefox":
                Configuration.browser = browser;
                break;
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }

        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000;
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        log.info(" Браузер: {}", Configuration.browser);
        log.info(" Headless: {}", Configuration.headless);
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info(" Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}


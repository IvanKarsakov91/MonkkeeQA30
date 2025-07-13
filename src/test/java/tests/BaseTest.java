package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import steps.LoginSteps;
import utils.ConfigReader;

public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected LoginSteps loginSteps = new LoginSteps();

    @Parameters({"browser", "requireLogin"})
    @BeforeMethod(alwaysRun = true)
    public void setupAndMaybeLogin(
            @Optional String browserParam,
            @Optional("true") String requireLoginParam
    ) {
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
        Configuration.pageLoadStrategy = "normal";

        logger.info("Запущен браузер: " + Configuration.browser);
        logger.info("Headless режим: " + Configuration.headless);

        boolean requireLogin = Boolean.parseBoolean(requireLoginParam);
        if (requireLogin) {
            logger.info("→ Выполняем логин перед тестом");
            loginSteps.performLogin("ivankarsakov91@gmail.com", "karsakov91");
            logger.info("→ Успешный вход");
        } else {
            logger.info("→ Логин не требуется для данного теста");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownAfterTest() {
        logger.info("Закрытие браузера");
        Selenide.closeWebDriver();
    }
}





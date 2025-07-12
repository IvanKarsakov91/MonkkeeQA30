package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;

public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browserParam) {
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

        logger.info("Запуск браузера: " + Configuration.browser);
        logger.info("Headless режим: " + Configuration.headless);
        logger.info("Размер окна: " + Configuration.browserSize);

        String startUrl = ConfigReader.get("startUrl");
        if (startUrl != null && !startUrl.isBlank()) {
            Selenide.open(startUrl);
            logger.info("Открыт стартовый URL: " + startUrl);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Selenide.closeWebDriver();
        logger.info("Закрытие браузера");
    }
}


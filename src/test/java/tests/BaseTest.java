package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.ConfigReader;

import java.util.UUID;

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

    @Step("Настройка браузера: {browserParam}")
    private void configureBrowser(String browserParam) {
        String browser = (browserParam != null && !browserParam.isBlank())
                ? browserParam
                : ConfigReader.get("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                Configuration.browser = "chrome";

                ChromeOptions chromeOptions = new ChromeOptions();
                // Уникальный профиль для каждого запуска
                String uuid = UUID.randomUUID().toString();
                chromeOptions.addArguments("--user-data-dir=/tmp/profile-" + uuid);
                chromeOptions.addArguments("--window-size=1280,800");

                if (Boolean.parseBoolean(ConfigReader.get("headless"))) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                }

                Configuration.browserCapabilities = chromeOptions;
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Users\\SofIvDar\\Downloads\\edgedriver_win32\\msedgedriver.exe");
                Configuration.browser = "edge";
                break;

            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }

        Configuration.timeout = 6000;

        log.info("Браузер: {}", Configuration.browser);
        log.info("Headless: {}", ConfigReader.get("headless"));
        log.info("Размер окна: 1280x800");
    }

    @AfterMethod(alwaysRun = true)
    @Step("Завершение теста")
    public void tearDownAfterTest() {
        log.info("Закрытие браузера после теста");
        Selenide.closeWebDriver();
    }
}




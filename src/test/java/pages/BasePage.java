package pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);

    public String getCurrentUrl() {
        String url = WebDriverRunner.url();
        log.info(" Текущий URL: {}", url);
        return url;
    }

    public WebDriver getDriver() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        log.info(" WebDriver получен: {}", driver.getClass().getSimpleName());
        return driver;
    }

    public void refreshPage() {
        log.info(" Обновление страницы");
        getDriver().navigate().refresh();
    }
}


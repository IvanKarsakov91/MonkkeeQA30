package pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;

public class BasePage {

    public String getCurrentUrl() {
        return WebDriverRunner.url();
    }

    public WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }
}

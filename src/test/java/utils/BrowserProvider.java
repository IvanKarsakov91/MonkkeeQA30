package utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.UUID;

public class BrowserProvider {

    public static void setup() {
        Configuration.browser = "chrome";

        ChromeOptions chromeOptions = new ChromeOptions();
        String uuid = UUID.randomUUID().toString();
        chromeOptions.addArguments("--user-data-dir=/tmp/profile-" + uuid);
        chromeOptions.addArguments("--window-size=1280,800");

        if (Boolean.parseBoolean(ConfigReader.get("headless"))) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
        }

        Configuration.browserCapabilities = chromeOptions;
        Configuration.timeout = 6000;
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));
    }
}





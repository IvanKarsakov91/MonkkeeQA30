package utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.UUID;

public class BrowserProvider {

    public static void setup() {
        String browser = ConfigReader.get("browser"); // chrome / edge

        switch (browser.toLowerCase()) {
            case "chrome":
                Configuration.browser = "chrome";

                ChromeOptions chromeOptions = new ChromeOptions();

                // Уникальный user-data-dir для каждого запуска
                String uuid = UUID.randomUUID().toString();
                chromeOptions.addArguments("--user-data-dir=/tmp/profile-" + uuid);
                chromeOptions.addArguments("--window-size=1920,1080");

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
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));
    }
}




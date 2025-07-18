package utils;

import com.codeborne.selenide.Configuration;

public class BrowserProvider {

    public static void setup() {
        String browser = ConfigReader.get("browser"); // chrome / edge

        switch (browser.toLowerCase()) {
            case "chrome":
                Configuration.browser = "chrome";
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Users\\SofIvDar\\Downloads\\edgedriver_win32\\msedgedriver.exe");
                Configuration.browser = "edge";
                break;
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }

        Configuration.timeout = 6000;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));
    }
}



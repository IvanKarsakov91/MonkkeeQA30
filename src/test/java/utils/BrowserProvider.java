package utils;

import com.codeborne.selenide.Configuration;

public class BrowserProvider {

    public static void setup() {
        Configuration.browser = ConfigReader.get("browser"); // chrome / firefox
        Configuration.timeout = 6000;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless")); // true / false
    }
}

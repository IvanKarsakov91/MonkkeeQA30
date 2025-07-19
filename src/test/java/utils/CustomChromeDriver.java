package utils;

import com.codeborne.selenide.WebDriverProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.UUID;

public class CustomChromeDriver implements WebDriverProvider {

    private static final Logger log = LogManager.getLogger(CustomChromeDriver.class);

    @Override
    public WebDriver createDriver(org.openqa.selenium.Capabilities ignored) {
        ChromeOptions options = new ChromeOptions();

        String profilePath = System.getProperty("user.home") + "/.chrome-profile-" + UUID.randomUUID();
        log.info("Инициализация Chrome с профилем: {}", profilePath);

        options.addArguments("--user-data-dir=" + profilePath);
        options.addArguments("--window-size=1280,800");
        options.addArguments("--disable-gpu");
        // options.addArguments("--headless"); // включать вручную, если нужно

        return new ChromeDriver(options);
    }
}


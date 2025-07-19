package utils;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.UUID;

public class CustomChromeDriver implements WebDriverProvider {
    @Override
    public WebDriver createDriver(org.openqa.selenium.Capabilities ignored) {
        ChromeOptions options = new ChromeOptions();

        String profilePath = System.getProperty("user.home") + "/.chrome-profile-" + UUID.randomUUID();
        System.out.println("Chrome profile: " + profilePath);

        options.addArguments("--user-data-dir=" + profilePath);
        options.addArguments("--window-size=1280,800");
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");

        return new ChromeDriver(options);
    }
}


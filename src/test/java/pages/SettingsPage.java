package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SettingsPage {

    public SelenideElement settingsButton = $("a[href*='settings']");
    public SelenideElement languageDropdown = $("#language");
    public SelenideElement saveButton = $("button[type='submit']");

    public void openSettings() {
        settingsButton.click();
    }

    public void selectLanguage(String languageCode) {
        languageDropdown.selectOptionByValue(languageCode); // "fr" для французского
    }

    public void saveSettings() {
        saveButton.click();
    }
}

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SettingsPage {

    private final SelenideElement settingsContainer =
            $$("div.user-menu__btn").findBy(text("Settings"));

    private final SelenideElement languageDropdown = $("select[name='selectLocale']");
    private final SelenideElement saveButton = $("button[type='submit']");

    public void openSettings() {
        settingsContainer.shouldBe(visible, enabled).click();
    }

    public void selectLanguage(String code) {
        languageDropdown.shouldBe(visible, enabled).selectOptionByValue(code);
    }

    public SelenideElement languageOption(String code) {
        return languageDropdown.$("option[value='" + code + "']");
    }

    public void saveSettings() {
        saveButton.shouldBe(visible, enabled).click();
    }
}



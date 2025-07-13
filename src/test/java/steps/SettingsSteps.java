package steps;

import io.qameta.allure.Step;
import pages.SettingsPage;

import static com.codeborne.selenide.Condition.selected;

public class SettingsSteps {

    private final SettingsPage settingsPage = new SettingsPage();

    @Step("Открыть настройки")
    public void openSettings() {
        settingsPage.openSettings();
    }

    @Step("Выбрать язык: {languageCode}")
    public void changeLanguage(String languageCode) {
        settingsPage.selectLanguage(languageCode);
        settingsPage.saveSettings();
    }

    @Step("Проверить, что выбран язык: {languageCode}")
    public void verifyLanguageIsSet(String languageCode) {
        settingsPage.languageOption(languageCode).shouldBe(selected);
    }
}


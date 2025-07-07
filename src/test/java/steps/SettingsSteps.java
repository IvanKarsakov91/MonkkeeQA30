package steps;

import io.qameta.allure.Step;
import pages.SettingsPage;

public class SettingsSteps {

    SettingsPage settingsPage = new SettingsPage();

    @Step("Открыть настройки")
    public void openSettings() {
        settingsPage.openSettings();
    }

    @Step("Выбрать язык: {languageCode}")
    public void changeLanguage(String languageCode) {
        settingsPage.selectLanguage(languageCode);
        settingsPage.saveSettings();
    }
}

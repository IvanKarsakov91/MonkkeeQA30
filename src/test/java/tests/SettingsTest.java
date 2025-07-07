package tests;

import org.testng.annotations.Test;
import steps.SettingsSteps;

public class SettingsTest {

    SettingsSteps settingsSteps = new SettingsSteps();

    @Test(groups = {"regression"})
    public void testChangeLanguageToFrench() {
        settingsSteps.openSettings();
        settingsSteps.changeLanguage("fr");
        // Проверка, что язык изменён
    }
}

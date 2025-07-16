package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.SettingsPage;

@Epic("5. Настройки")
@Feature("5.1 Смена языка")
public class SettingsTest extends BaseTest {

    private final SettingsPage settingsPage = new SettingsPage();

    @Test(priority = 1, groups = {"smoke"}, description = "5.1. Смена языка на немецкий")
    @Story("5.1 Язык: Deutsch")
    @Severity(SeverityLevel.CRITICAL)
    public void testChangeLanguageToGerman() {
        settingsPage.openSettings();
        settingsPage.changeLanguage("de");
        assert settingsPage.isLanguageSelected("de");
    }
}




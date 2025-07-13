package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import steps.SettingsSteps;

@Epic("5. Настройки")
@Feature("5.1 Смена языка")
public class SettingsTest extends BaseTest {

    private final SettingsSteps settingsSteps = new SettingsSteps();

    @Test(
            priority = 1,
            groups = {"smoke"},
            description = "5.1. Смена языка на немецкий"
    )
    @Story("5.1 Язык: Deutsch")
    @Severity(SeverityLevel.CRITICAL)
    public void testChangeLanguageToGerman() {
        settingsSteps.openSettings();
        settingsSteps.changeLanguage("de");
        settingsSteps.verifyLanguageIsSet("de");

        System.out.println(" Язык успешно установлен: Deutsch");
    }
}


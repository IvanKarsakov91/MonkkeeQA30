package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.LanguagePage;

@Epic("5. Настройки")
@Feature("5.1 Смена языка без авторизации")
public class LanguageTest extends BaseTest {

    private final LanguagePage languagePage = new LanguagePage();

    @Test(priority = 1, groups = {"smoke"}, description = "5.1. Смена языка интерфейса на Deutsch (публичная страница)")
    @Story("5.1 Язык: Deutsch — публичная страница")
    @Severity(SeverityLevel.CRITICAL)
    public void testSwitchToGermanWithoutLogin() {
        languagePage.openLandingPage();            // 🔹 открываем https://monkkee.com/en
        languagePage.openLanguageDropdown();       // 🔹 кликаем на language-switcher
        languagePage.selectGerman();               // 🔹 выбираем Deutsch

        assert languagePage.isGermanVersionOpened() :
                "Не произошёл переход на /de — язык не переключился на Deutsch";
    }
}

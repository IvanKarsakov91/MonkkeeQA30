package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SettingsPage {

    private static final Logger log = LogManager.getLogger(SettingsPage.class);

    private final SelenideElement settingsContainer =
            $$("div.user-menu__btn").findBy(text("Settings"));
    private final SelenideElement languageDropdown = $("select[name='selectLocale']");
    private final SelenideElement saveButton = $("button[type='submit']");

    @Step("Открыть настройки")
    public void openSettings() {
        settingsContainer.shouldBe(visible, enabled).click();
        log.info("Открыт блок настроек");
    }

    @Step("Выбрать язык: {code}")
    public void changeLanguage(String code) {
        languageDropdown.shouldBe(visible, enabled).selectOptionByValue(code);
        saveButton.shouldBe(visible, enabled).click();
        log.info("Выбран язык: {}", code);
        Allure.addAttachment("Выбранный язык", code);
    }

    @Step("Проверка: выбран язык — {code}")
    public boolean isLanguageSelected(String code) {
        return getLanguageOption(code).shouldBe(visible).isSelected();
    }

    @Step("Получить элемент языка по коду: {code}")
    public SelenideElement getLanguageOption(String code) {
        return languageDropdown.$("option[value='" + code + "']");
    }
}




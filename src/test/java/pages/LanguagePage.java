package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LanguagePage {

    private static final Logger log = LogManager.getLogger(LanguagePage.class);

    private final SelenideElement switcher = $("#locale-menu");
    private final SelenideElement germanLink = $("a.dropdown-item[href='/de']");

    @Step("Открыть публичную страницу")
    public void openLandingPage() {
        Selenide.open("https://monkkee.com/en");
        log.info("Открыта страница https://monkkee.com/en");
    }

    @Step("Открыть выпадающее меню выбора языка")
    public void openLanguageDropdown() {
        switcher.shouldBe(visible, enabled).click();
        log.info("Открыто меню выбора языка");
    }

    @Step("Выбрать Deutsch")
    public void selectGerman() {
        germanLink.shouldBe(visible).click();
        log.info("Выбран Deutsch → переход на /de");
    }

    @Step("Проверить, что перешли на немецкую версию")
    public boolean isGermanVersionOpened() {
        String currentUrl = Selenide.webdriver().driver().url();
        log.info("Текущий URL: {}", currentUrl);
        return currentUrl.contains("/de");
    }
}

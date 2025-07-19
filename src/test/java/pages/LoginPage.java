package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private static final String loginUrl = "https://monkkee.com/app/#/";
    private static final String successRedirectUrl = "/#/entries";

    private final SelenideElement emailInput = $("#login");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement entriesLink = $("a[href='#/entries']");
    private final SelenideElement logoutLabel = $$("span.user-menu__btn-label").findBy(text("Logout"));

    @Step("Открытие страницы входа")
    public void openLoginPage() {
        open(loginUrl);
        emailInput.should(appear, Duration.ofSeconds(10));
        passwordInput.should(appear, Duration.ofSeconds(10));
        log.info("Открыта страница логина: {}", loginUrl);
    }

    @Step("Попытка входа: {email}")
    public void login(String email, String password) {
        emailInput.clear();
        emailInput.setValue(email).shouldBe(not(empty));
        passwordInput.clear();
        passwordInput.setValue(password).shouldBe(not(empty));
        submitButton.shouldBe(enabled, Duration.ofSeconds(5)).click();
        log.info("Введены данные пользователя: {}", email);
    }

    @Step("Переход к записям вручную")
    public void clickEntriesLinkIfVisible() {
        entriesLink.shouldBe(visible, Duration.ofSeconds(5));
        if (entriesLink.exists() && entriesLink.isDisplayed()) {
            entriesLink.click();
            log.info("Нажата ссылка 'Go to your entries'");
        }
    }

    @Step("Ожидание редиректа на /#/entries")
    public boolean waitForRedirectToEntries() {
        for (int i = 0; i < 20; i++) {
            if (WebDriverRunner.url().contains(successRedirectUrl)) return true;
            sleep(250);
        }
        log.warn("Не удалось дождаться перехода на /#/entries");
        return false;
    }

    @Step("Вход с проверкой редиректа")
    public void loginAndWaitForRedirect(String email, String password) {
        openLoginPage();
        login(email, password);

        if (!waitForRedirectToEntries()) {
            clickEntriesLinkIfVisible();
            if (!waitForRedirectToEntries()) {
                String currentUrl = WebDriverRunner.url();
                log.error("Редирект не выполнен. Текущий URL: {}", currentUrl);
                throw new IllegalStateException("Ожидаемый переход на /#/entries не выполнен");
            }
        }
    }

    @Step("Вход через пользователя: {user.email}")
    public void loginWith(User user) {
        loginAndWaitForRedirect(user.getEmail(), user.getPassword());
    }

    @Step("Выход из аккаунта")
    public void logout() {
        logoutLabel.shouldBe(visible, Duration.ofSeconds(10)).click();
        log.info("Выполнен выход из аккаунта");
    }

    @Step("Ожидание возврата на /#/")
    public boolean waitForRedirectToLoginPage() {
        for (int i = 0; i < 20; i++) {
            if (WebDriverRunner.url().endsWith("/#/")) return true;
            sleep(200);
        }
        log.warn("Редирект на /#/ после выхода не выполнен");
        return false;
    }
}




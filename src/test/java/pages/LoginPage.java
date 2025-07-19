package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
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

    private final SelenideElement emailField = $("#login");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement loginButton = $("button[type='submit']");
    private final SelenideElement goToEntriesLink = $("a[href='#/entries']");

    @Step("Открытие страницы логина")
    public void openLoginPage() {
        open(loginUrl);
        emailField.should(appear, Duration.ofSeconds(5)).shouldBe(enabled);
        passwordField.should(appear, Duration.ofSeconds(5)).shouldBe(enabled);
        log.info("SPA логин-страница загружена: {}", loginUrl);
        Allure.addAttachment("Login URL", loginUrl);
    }

    @Step("Логин под email: {email}")
    public void login(String email, String password) {
        emailField.clear();
        emailField.setValue(email).shouldBe(not(empty));
        passwordField.clear();
        passwordField.setValue(password).shouldBe(not(empty));
        loginButton.shouldBe(enabled, Duration.ofSeconds(3)).click();
        log.info("Попытка логина: {}", email);
        Allure.addAttachment("Попытка логина", email);
    }

    @Step("Переход по ссылке 'Go to your entries'")
    public void navigateToEntries() {
        goToEntriesLink.shouldBe(visible, Duration.ofSeconds(5));
        if (goToEntriesLink.isDisplayed() && goToEntriesLink.getSize().getHeight() > 0) {
            goToEntriesLink.click();
            log.info("Нажата ссылка 'Go to your entries'");
            Allure.addAttachment("Переход на entries", WebDriverRunner.url());
        } else {
            log.warn("Ссылка 'Go to your entries' неактивна — пропускаем клик");
            Allure.addAttachment("Состояние ссылки", "Ссылка неактивна или имеет нулевой размер");
        }
    }

    @Step("Вход и переход на /#/entries для email: {email}")
    public void performLoginAndGoToEntries(String email, String password) {
        openLoginPage();
        login(email, password);

        if (!verifyRedirectToEntries()) {
            navigateToEntries();
            if (!verifyRedirectToEntries()) {
                String currentUrl = WebDriverRunner.url();
                log.error("Редирект на entries не произошёл. Текущий URL: {}", currentUrl);
                Allure.addAttachment("Ошибка редиректа", currentUrl);
                throw new IllegalStateException("Редирект на /#/entries не произошёл.");
            }
        }
    }

    @Step("Логин с валидным пользователем: {user.email}")
    public void loginWithValidUser(User user) {
        performLoginAndGoToEntries(user.getEmail(), user.getPassword());
    }

    public void logout() {
        SelenideElement logoutLabel = $$("span.user-menu__btn-label").findBy(text("Logout"));
        logoutLabel.shouldBe(visible, Duration.ofSeconds(5)).click();
        log.info("Нажата кнопка 'Logout'");
        Allure.addAttachment("Выход из аккаунта", "Кнопка Logout нажата");
    }

    public boolean verifyRedirectToLoginPage() {
        for (int i = 0; i < 15; i++) {
            if (WebDriverRunner.url().endsWith("/#/")) {
                log.info("Редирект подтверждён на /#/");
                Allure.addAttachment("Redirect after logout", WebDriverRunner.url());
                return true;
            }
            sleep(200);
        }
        return false;
    }

    public boolean verifyRedirectToEntries() {
        for (int i = 0; i < 15; i++) {
            if (WebDriverRunner.url().contains(successRedirectUrl)) return true;
            sleep(150);
        }
        return false;
    }
}


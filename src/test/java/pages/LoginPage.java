package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
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
    private final SelenideElement reminderLink = $("a[href*='password_reminder']");

    public void openLoginPage() {
        open(loginUrl);

        if (!emailField.exists()) {
            log.error("Элемент #login отсутствует. Страница не прогрузилась или структура изменилась.");
            Allure.addAttachment("Ошибка логина", "Элемент #login отсутствует на " + loginUrl);
            throw new IllegalStateException("Поле логина не найдено.");
        }

        emailField.shouldBe(visible, Duration.ofSeconds(15)).shouldBe(enabled);
        passwordField.shouldBe(visible, Duration.ofSeconds(15)).shouldBe(enabled);

        log.info("Страница логина загружена: {}", loginUrl);
        Allure.addAttachment("Login URL", loginUrl);
    }

    public void login(String email, String password) {
        emailField.setValue(email);
        passwordField.setValue(password);
        loginButton.shouldBe(visible, Duration.ofSeconds(10)).click();

        log.info("Попытка логина: {}", email);
        Allure.addAttachment("Попытка логина", email);
    }

    public void loginWithValidUser(User user) {
        openLoginPage();
        login(user.getEmail(), user.getPassword());

        log.info("Логин через объект User: {}", user.getEmail());
        Allure.addAttachment("Логин через User", user.getEmail());
    }

    public void performLogin(String email, String password) {
        openLoginPage();
        login(email, password);

        for (int i = 0; i < 30; i++) {
            if (WebDriverRunner.url().contains(successRedirectUrl)) {
                log.info("Авторизация успешна. Перешли на {}", WebDriverRunner.url());
                Allure.addAttachment("Успешный редирект", WebDriverRunner.url());
                return;
            }
            sleep(300);
        }

        String currentUrl = WebDriverRunner.url();
        log.error("Редирект не выполнен. Текущий URL: {}", currentUrl);
        Allure.addAttachment("Ошибка авторизации", currentUrl);
        throw new IllegalStateException("Редирект на /#/entries не произошёл.");
    }

    public boolean verifyRedirectToEntries() {
        for (int i = 0; i < 30; i++) {
            if (WebDriverRunner.url().contains(successRedirectUrl)) return true;
            sleep(300);
        }
        return false;
    }

    public void openPasswordReminder() {
        reminderLink.shouldBe(visible, Duration.ofSeconds(10)).click();

        log.info("Открыта форма восстановления пароля");
        Allure.addAttachment("Password Reminder", WebDriverRunner.url());
    }

    public void refreshPage() {
        refresh();
        log.info("Страница логина вручную обновлена");
    }
}







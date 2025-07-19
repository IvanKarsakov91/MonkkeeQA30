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
        private static final String entriesUrl = "https://monkkee.com/app/#/entries";
        private static final String postLoginUrl = "https://monkkee.com/app/#/";

        private final SelenideElement emailInput = $("#login");
        private final SelenideElement passwordInput = $("#password");
        private final SelenideElement submitButton = $("button[type='submit']");
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
            emailInput.setValue(email);
            passwordInput.clear();
            passwordInput.setValue(password);
            submitButton.shouldBe(enabled, Duration.ofSeconds(5)).click();
            log.info("Введены данные пользователя: {}", email);
        }

        @Step("Переход напрямую на /#/entries")
        public void goToEntriesPageDirect() {
            open(entriesUrl);
            for (int i = 0; i < 40; i++) {
                if (WebDriverRunner.url().contains("/#/entries")) return;
                sleep(500);
            }
            String currentUrl = WebDriverRunner.url();
            log.error("Не удалось перейти на /#/entries. URL: {}", currentUrl);
            throw new IllegalStateException("Переход на /#/entries не выполнен");
        }

        @Step("Вход с проверкой перехода на /#/entries")
        public void loginAndWaitForRedirect(String email, String password) {
            openLoginPage();
            login(email, password);

            for (int i = 0; i < 40; i++) {
                if (WebDriverRunner.url().contains("/#/entries")) return;
                sleep(500);
            }

            log.warn("Редирект не выполнен, выполняем ручной переход");
            goToEntriesPageDirect();

            if (!WebDriverRunner.url().contains("/#/entries")) {
                String currentUrl = WebDriverRunner.url();
                log.error("Редирект не выполнен. URL: {}", currentUrl);
                throw new IllegalStateException("Не удалось попасть на /#/entries");
            }
        }

        @Step("Вход с апрувом: переход хотя бы на /#/")
        public void loginAndApprove(String email, String password) {
            openLoginPage();
            login(email, password);

            for (int i = 0; i < 40; i++) {
                if (WebDriverRunner.url().startsWith(postLoginUrl)) {
                    log.info("Переход после логина подтверждён: {}", WebDriverRunner.url());
                    return;
                }
                sleep(500);
            }

            String currentUrl = WebDriverRunner.url();
            log.error("Не удалось попасть на целевую страницу. URL: {}", currentUrl);
            throw new IllegalStateException("Не удалось подтвердить переход после логина");
        }

        @Step("Вход через объект User")
        public void loginWith(User user) {
            loginAndWaitForRedirect(user.getEmail(), user.getPassword());
        }

        @Step("Выход из аккаунта")
        public void logout() {
            logoutLabel.should(appear, Duration.ofSeconds(10)).click();
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


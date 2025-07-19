package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private static final String loginUrl = "https://monkkee.com/app/#/";
    private static final String entriesUrl = "https://monkkee.com/app/#/entries";

    private final SelenideElement emailInput = $("#login");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement logoutLabel = $$("span.user-menu__btn-label").findBy(text("Logout"));
    private final SelenideElement createEntryButton = $("#create-entry");

    @Step("Открытие страницы логина")
    public void openLoginPage() {
        open(loginUrl);
        emailInput.should(appear, Duration.ofSeconds(10));
        passwordInput.should(appear, Duration.ofSeconds(10));
        log.info("Открыта страница логина");
    }

    @Step("Ввод email и пароля + ожидание перехода")
    public void login(String email, String password) {
        emailInput.clear();
        emailInput.setValue(email);
        passwordInput.clear();
        passwordInput.setValue(password);
        submitButton.shouldBe(enabled, Duration.ofSeconds(5)).click();
        log.info("Данные логина введены: {}", email);

        for (int i = 0; i < 20; i++) {
            if (!WebDriverRunner.url().endsWith("/#/")) {
                log.info("Переход после логина подтверждён: {}", WebDriverRunner.url());
                return;
            }
            sleep(500);
        }
        log.warn("После логина остался на /#/: {}", WebDriverRunner.url());
    }

    @Step("Логин и переход напрямую на /#/entries")
    public void loginAndGoToEntries(String email, String password) {
        openLoginPage();
        login(email, password);
        open(entriesUrl);

        for (int i = 0; i < 40; i++) {
            if (WebDriverRunner.url().contains("/#/entries")) {
                log.info("Переход на /#/entries подтверждён");
                return;
            }
            sleep(500);
        }

        throw new IllegalStateException("Не удалось открыть /#/entries после логина");
    }

    @Step("Проверка наличия иконки пера (создание новой записи)")
    public boolean isPenIconPresent() {
        return $("path#Icon_awesome-pen-nib")
                .should(appear, Duration.ofSeconds(10))
                .exists();
    }

    @Step("Нажатие на кнопку 'Create an entry'")
    public void clickCreateEntryButton() {
        createEntryButton.shouldBe(enabled, Duration.ofSeconds(10)).click();
        log.info("Кнопка создания новой записи нажата");
    }

    @Step("Выход из аккаунта")
    public void logout() {
        logoutLabel.should(appear, Duration.ofSeconds(10)).click();
        log.info("Пользователь вышел из системы");
    }

    @Step("Ожидание редиректа на /#/")
    public boolean waitForRedirectToLoginPage() {
        for (int i = 0; i < 20; i++) {
            if (WebDriverRunner.url().endsWith("/#/")) return true;
            sleep(200);
        }
        log.warn("Редирект после выхода не выполнен");
        return false;
    }
}


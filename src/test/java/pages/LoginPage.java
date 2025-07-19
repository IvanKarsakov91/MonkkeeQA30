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

    private final SelenideElement emailInput = $("#login");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement logoutLabel = $$("span.user-menu__btn-label").findBy(text("Logout"));
    private final SelenideElement createEntryButton = $("#create-entry");

    @Step("Открытие страницы логина")
    public void openLoginPage() {
        open("https://monkkee.com/app/#/");
        sleep(500);
        emailInput.should(appear, Duration.ofSeconds(10));
        sleep(500);
        passwordInput.should(appear, Duration.ofSeconds(10));
        sleep(500);
        log.info("Открыта страница логина");
    }

    @Step("Ввод email и пароля с паузами, затем нажатие на 'Login'")
    public void login(String email, String password) {
        emailInput.clear();
        sleep(300);
        emailInput.setValue(email);
        sleep(500);

        passwordInput.clear();
        sleep(300);
        passwordInput.setValue(password);
        sleep(500);

        submitButton.shouldBe(enabled, Duration.ofSeconds(5));
        sleep(300);
        submitButton.click();
        sleep(500);
        log.info("Данные логина введены: {}", email);

        for (int i = 0; i < 40; i++) {
            if (WebDriverRunner.url().contains("/#/entries")) {
                log.info("Переход на /#/entries подтверждён автоматически: {}", WebDriverRunner.url());
                sleep(500);
                return;
            }
            sleep(500);
        }
        log.warn("После логина не произошёл переход на /#/entries: {}", WebDriverRunner.url());
    }

    @Step("Нажатие на кнопку 'Create an entry'")
    public void clickCreateEntryButton() {
        createEntryButton.shouldBe(enabled, Duration.ofSeconds(10));
        sleep(500);
        createEntryButton.click();
        sleep(500);
        log.info("Кнопка создания новой записи нажата");
    }

    @Step("Проверка наличия иконки пера (создание новой записи)")
    public boolean isPenIconPresent() {
        sleep(300);
        return $("path#Icon_awesome-pen-nib")
                .should(appear, Duration.ofSeconds(10))
                .exists();
    }

    @Step("Выход из аккаунта")
    public void logout() {
        logoutLabel.should(appear, Duration.ofSeconds(10));
        sleep(500);
        logoutLabel.click();
        sleep(500);
        log.info("Пользователь вышел из системы");
    }

    @Step("Ожидание редиректа на /#/")
    public boolean waitForRedirectToLoginPage() {
        for (int i = 0; i < 20; i++) {
            if (WebDriverRunner.url().endsWith("/#/")) {
                sleep(300);
                return true;
            }
            sleep(200);
        }
        log.warn("Редирект после выхода не выполнен");
        return false;
    }
}

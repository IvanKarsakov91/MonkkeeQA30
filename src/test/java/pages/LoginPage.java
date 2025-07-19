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

    private final SelenideElement emailInput = $("#login");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement logoutButton = $$("span.user-menu__btn-label").findBy(text("Logout"));

    @Step("Открытие страницы логина")
    public void openLoginPage() {
        open(loginUrl);
        sleep(500);
        clearBrowserSession(); // выполняем JS уже после инициализации WebDriver
        emailInput.should(appear, Duration.ofSeconds(10));
        sleep(300);
        passwordInput.should(appear, Duration.ofSeconds(10));
        sleep(300);
        log.info("Страница логина загружена");
    }

    @Step("Логин по email и паролю")
    public void login(String email, String password) {
        emailInput.clear();
        sleep(200);
        emailInput.setValue(email);
        sleep(300);
        passwordInput.clear();
        sleep(200);
        passwordInput.setValue(password);
        sleep(300);
        submitButton.shouldBe(enabled, Duration.ofSeconds(5));
        submitButton.click();

        for (int i = 0; i < 30; i++) {
            if (WebDriverRunner.url().contains("/#/entries")) {
                log.info("Переход на /#/entries — успешный логин");
                return;
            }
            sleep(500);
        }

        throw new IllegalStateException("После логина не произошёл переход на /#/entries");
    }

    @Step("Очистка sessionStorage и localStorage")
    public void clearBrowserSession() {
        executeJavaScript("window.localStorage.clear(); window.sessionStorage.clear();");
        log.info("Очистка sessionStorage и localStorage завершена");
    }

    @Step("Выход из аккаунта")
    public void logout() {
        logoutButton.shouldBe(visible, Duration.ofSeconds(10));
        sleep(300);
        logoutButton.click();
        sleep(500);
        log.info("Пользователь вышел из аккаунта");
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


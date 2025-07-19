package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
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
        open("https://monkkee.com/app/#/logout");
        sleep(800);
        open(loginUrl);
        sleep(600);
        executeJavaScript("window.localStorage.clear(); window.sessionStorage.clear();");
        log.info("Очистка sessionStorage и localStorage выполнена");

        emailInput.shouldBe(visible, Duration.ofSeconds(10));
        passwordInput.shouldBe(visible, Duration.ofSeconds(10));
        sleep(300);
        log.info("Страница логина загружена");
    }

    @Step("Логин по email и паролю")
    public void login(String email, String password) {
        emailInput.clear();
        emailInput.setValue(email);
        passwordInput.clear();
        passwordInput.setValue(password);
        submitButton.shouldBe(visible, Duration.ofSeconds(5)).click();

        for (int i = 0; i < 30; i++) {
            String url = WebDriverRunner.url();
            if (url.endsWith("/#/")) {
                log.info("Редирект на /#/ подтверждён");
                goToEntriesPage();
                return;
            }
            if (url.contains("/#/entries")) {
                log.info("Переход на /#/entries подтверждён: {}", url);
                return;
            }
            sleep(500);
        }

        throw new IllegalStateException("После логина не произошёл переход на /#/ или /#/entries");
    }

    public void goToEntriesPage() {
        executeJavaScript("document.querySelector(\"a[href='#/entries']\")?.click()");
        sleep(1000);
        log.info("Переход на страницу записей выполнен через JS");
    }

    @Step("Выход из аккаунта")
    public void logout() {
        logoutButton.shouldBe(visible, Duration.ofSeconds(10));
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

package steps;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class LoginSteps {

    @Step("Открываем страницу логина")
    public void openLoginPage() {
        open("https://monkkee.com/app/#/login");

        // Убеждаемся, что поля появились
        $("#login").shouldBe(visible, Duration.ofSeconds(10));
        $("#password").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Step("Вводим email: {email} и пароль: {password}")
    public void login(String email, String password) {
        $("#login").shouldBe(visible, Duration.ofSeconds(10)).setValue(email);
        $("#password").shouldBe(visible, Duration.ofSeconds(10)).setValue(password);
        $("button[type='submit']").shouldBe(visible, enabled).click();
    }

    @Step("Полный логин под пользователем: {email}")
    public void performLogin(String email, String password) {
        openLoginPage();
        login(email, password);

        // Ждём переход или появление ссылки
        for (int i = 0; i < 20; i++) {
            if (url().contains("/entries")) return;
            sleep(300);
        }

        // Если остались на /#/ — пробуем нажать "Go to your entries"
        if (url().endsWith("/#/")) {
            $("a[href='#/entries']").shouldBe(visible);
            executeJavaScript("arguments[0].click();", $("a[href='#/entries']"));
            sleep(500);
        }

        if (!url().contains("/entries")) {
            throw new IllegalStateException(" Логин не удался. URL после входа: " + url());
        }
    }

    @Step("Обновляем страницу вручную")
    public void refreshPage() {
        refresh();
    }
}















package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.LoginPage;
import steps.LoginSteps;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("2. Авторизация пользователя")
@Feature("2.0. Проверка формы логина")
public class LoginTest extends BaseTest {

    LoginSteps loginSteps = new LoginSteps();
    LoginPage loginPage = new LoginPage();

    @Test(
            groups = {"smoke"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.1. Успешный вход при корректных данных"
    )
    @Story("2.1. Валидные email и пароль")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        loginSteps.openLoginPage();
        loginSteps.login("user@mail.com", "Password123");

        assert WebDriverRunner.url().contains("/entries");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.2. Вход с пустыми всеми полями"
    )
    @Story("2.2. Пустые email и пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyFields() {
        loginSteps.openLoginPage();
        loginSteps.login("", "");

        assert WebDriverRunner.url().contains("/app/#/");
        assert !WebDriverRunner.url().contains("/entries");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.3. Вход с пустым паролем"
    )
    @Story("2.3. Пустой пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword() {
        loginSteps.openLoginPage();
        loginSteps.login("user@mail.com", "");

        assert WebDriverRunner.url().contains("/app/#/");
        assert !WebDriverRunner.url().contains("/entries");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.4. Вход с пустым email"
    )
    @Story("2.4. Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail() {
        loginSteps.openLoginPage();
        loginSteps.login("", "Password123");

        assert WebDriverRunner.url().contains("/app/#/");
        assert !WebDriverRunner.url().contains("/entries");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.5. Отправка запроса на восстановление пароля"
    )
    @Story("2.5. Ссылка на восстановление пароля")
    @Severity(SeverityLevel.MINOR)
    public void testPasswordReminder() {
        loginSteps.openLoginPage();
        $("a[href*='password_reminder']").click();

        assert WebDriverRunner.url().contains("/app/#/password_reminder");
    }
}



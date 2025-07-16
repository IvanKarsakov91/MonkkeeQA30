package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

@Epic("2. Авторизация пользователя")
@Feature("2.0. Проверка формы логина")
public class LoginTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    @Test(
            groups = {"smoke"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.1. Успешный вход при корректных данных"
    )
    @Story("2.1. Валидные email и пароль")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        loginPage.openLoginPage();
        loginPage.login("ivankarsakov91@gmail.com", "karsakov91");

        Assert.assertTrue(loginPage.verifyRedirectToEntries(),
                "После логина не выполнен переход на /#/entries");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.2. Вход с пустыми всеми полями"
    )
    @Story("2.2. Пустые email и пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyFields() {
        loginPage.openLoginPage();
        loginPage.login("", "");
        Assert.assertFalse(loginPage.verifyRedirectToEntries(),
                "Не должен происходить переход при пустых данных");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.3. Вход с пустым паролем"
    )
    @Story("2.3. Пустой пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword() {
        loginPage.openLoginPage();
        loginPage.login("user@mail.com", "");
        Assert.assertFalse(loginPage.verifyRedirectToEntries(),
                "Не должен происходить переход при пустом пароле");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.4. Вход с пустым email"
    )
    @Story("2.4. Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail() {
        loginPage.openLoginPage();
        loginPage.login("", "Password123");
        Assert.assertFalse(loginPage.verifyRedirectToEntries(),
                "Не должен происходить переход при пустом email");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.5. Отправка запроса на восстановление пароля"
    )
    @Story("2.5. Ссылка на восстановление пароля")
    @Severity(SeverityLevel.MINOR)
    public void testPasswordReminder() {
        loginPage.openLoginPage();
        loginPage.openPasswordReminder();
        Assert.assertTrue(WebDriverRunner.url().contains("password_reminder"),
                "Не выполнен переход на восстановление пароля");
    }
}




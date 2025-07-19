package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

@Epic("2. Авторизация пользователя")
@Feature("2.0. Проверка формы логина")
public class LoginTest extends BaseTest {

    @Test(groups = {"smoke"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.1. Проверить успешный вход при корректных данных")
    @Story("2.1. Валидные email и пароль")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        String email = System.getProperty("user", "user@mail.com");
        String password = System.getProperty("password", "Password123");

        loginPage.loginAndApprove(email, password);
        Assert.assertTrue(WebDriverRunner.url().contains("/#/"),
                "После логина должен быть переход хотя бы на /#/");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.2. Проверить вход с пустыми всеми полями")
    @Story("2.2. Пустые email и пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyFields() {
        loginPage.openLoginPage();
        loginPage.login("", "");
        Assert.assertFalse(WebDriverRunner.url().contains("/#/entries"),
                "Не должен происходить переход при пустых данных");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.3. Проверить вход с пустым паролем")
    @Story("2.3. Пустой пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword() {
        loginPage.openLoginPage();
        loginPage.login("user@mail.com", "");
        Assert.assertFalse(WebDriverRunner.url().contains("/#/entries"),
                "Не должен происходить переход при пустом пароле");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.4. Проверить вход с пустым email")
    @Story("2.4. Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail() {
        loginPage.openLoginPage();
        loginPage.login("", "Password123");
        Assert.assertFalse(WebDriverRunner.url().contains("/#/entries"),
                "Не должен происходить переход при пустом email");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.5. Проверить выход из аккаунта")
    @Story("2.5. Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogoutFromAccount() {
        String email = System.getProperty("user", "user@mail.com");
        String password = System.getProperty("password", "Password123");

        loginPage.loginAndApprove(email, password);
        loginPage.logout();
        Assert.assertTrue(loginPage.waitForRedirectToLoginPage(),
                "После выхода не произошёл переход на /app/#/");
    }
}





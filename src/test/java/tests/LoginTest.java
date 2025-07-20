package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("2. Авторизация пользователя")
@Feature("2.0. Проверка формы логина")
public class LoginTest extends BaseTest {

    @Test(groups = {"smoke"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.1. Проверить успешный вход при корректных данных")
    @Story("2.1. Валидные email и пароль")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());
        Assert.assertTrue(loginPage.isOnEntriesPage(), "Ожидался переход на /#/entries");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.2. Проверить вход с пустыми всеми полями")
    @Story("2.2. Пустые email и пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyFields() {
        loginPage.openLoginPage();
        loginPage.login("", "");
        Assert.assertFalse(loginPage.isOnEntriesPage(), "Переход не должен происходить при пустых данных");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.3. Проверить вход с пустым паролем")
    @Story("2.3. Пустой пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), "");
        Assert.assertFalse(loginPage.isOnEntriesPage(), "Переход не должен происходить при пустом пароле");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.4. Проверить вход с пустым email")
    @Story("2.4. Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail() {
        loginPage.openLoginPage();
        loginPage.login("", defaultUser.getPassword());
        Assert.assertFalse(loginPage.isOnEntriesPage(), "Переход не должен происходить при пустом email");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.5. Проверить выход из аккаунта")
    @Story("2.5. Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogoutFromAccount() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());
        loginPage.goToEntriesPage();
        loginPage.logout();
        Assert.assertTrue(loginPage.waitForRedirectToLoginPage(), "После выхода не произошёл переход на /#/");
    }
}


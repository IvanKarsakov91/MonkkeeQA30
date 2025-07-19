package tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("2. Авторизация пользователя")
@Feature("2.0. Проверка формы логина")
public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);
    private final LoginPage loginPage = new LoginPage();

    @Test(groups = {"smoke"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.1. Проверить успешный вход при корректных данных")
    @Story("2.1. Валидные email и пароль")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());

        SelenideElement entriesLink = $("a[href='#/entries']");
        entriesLink.should(appear, Duration.ofSeconds(15));

        executeJavaScript("arguments[0].scrollIntoView(true);", entriesLink);
        executeJavaScript("arguments[0].click();", entriesLink);

        for (int i = 0; i < 40; i++) {
            if (WebDriverRunner.url().contains("/#/entries")) {
                log.info("Переход на /#/entries подтверждён: {}", WebDriverRunner.url());
                return;
            }
            sleep(500);
        }

        throw new IllegalStateException("Клик выполнен, но переход на /#/entries не произошёл");
    }


    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.2. Проверить вход с пустыми всеми полями")
    @Story("2.2. Пустые email и пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyFields() {
        loginPage.openLoginPage();
        loginPage.login("", "");
        Assert.assertFalse(WebDriverRunner.url().contains("/#/entries"),
                "Переход не должен происходить при пустых данных");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.3. Проверить вход с пустым паролем")
    @Story("2.3. Пустой пароль")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), "");
        Assert.assertFalse(WebDriverRunner.url().contains("/#/entries"),
                "Переход не должен происходить при пустом пароле");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.4. Проверить вход с пустым email")
    @Story("2.4. Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail() {
        loginPage.openLoginPage();
        loginPage.login("", defaultUser.getPassword());
        Assert.assertFalse(WebDriverRunner.url().contains("/#/entries"),
                "Переход не должен происходить при пустом email");
    }

    @Test(groups = {"regression"}, retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "2.5. Проверить выход из аккаунта")
    @Story("2.5. Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogoutFromAccount() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());

        SelenideElement entriesLink = $("a[href='#/entries']");
        entriesLink.shouldBe(visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].click();", entriesLink);

        loginPage.logout();
        Assert.assertTrue(loginPage.waitForRedirectToLoginPage(),
                "После выхода не произошёл переход на /#/");
    }
}

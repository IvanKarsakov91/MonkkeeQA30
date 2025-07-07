package tests;

import pages.BasePage;
import org.testng.annotations.Test;
import steps.LoginSteps;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    LoginSteps loginSteps = new LoginSteps();
    BasePage basePage = new BasePage();

    @Test(groups = {"smoke"})
    public void testSuccessfulLogin() {
        loginSteps.openLoginPage();
        loginSteps.login("user@mail.com", "Password123");
        assertThat(basePage.getCurrentUrl()).contains("/entries");
    }

    @Test(groups = {"regression"})
    public void testLoginWithEmptyFields() {
        loginSteps.openLoginPage();
        loginSteps.login("", "");
        // TODO: Проверка сообщения об ошибке
    }

    @Test(groups = {"regression"})
    public void testLoginWithEmptyPassword() {
        loginSteps.openLoginPage();
        loginSteps.login("user@mail.com", "");
        // TODO: Проверка сообщения об ошибке
    }

    @Test(groups = {"regression"})
    public void testLoginWithEmptyEmail() {
        loginSteps.openLoginPage();
        loginSteps.login("", "Password123");
        // TODO: Проверка сообщения об ошибке
    }

    @Test(groups = {"regression"})
    public void testPasswordReminder() {
        loginSteps.openLoginPage();
        loginSteps.clickPasswordReminder();
        // TODO: Проверка перехода на страницу восстановления
    }
}

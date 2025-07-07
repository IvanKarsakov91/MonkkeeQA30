package tests;

import org.testng.annotations.Test;
import steps.LoginSteps;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    LoginSteps loginSteps = new LoginSteps();

    @Test(groups = {"smoke"})
    public void testSuccessfulLogin() {
        loginSteps.openLoginPage();
        loginSteps.login("user@mail.com", "Password123");
        assertThat(loginSteps.getCurrentUrl()).contains("/entries");
    }

    @Test(groups = {"regression"})
    public void testLoginWithEmptyFields() {
        loginSteps.openLoginPage();
        loginSteps.login("", "");
        // Проверка сообщения об ошибке
    }

    @Test(groups = {"regression"})
    public void testLoginWithEmptyPassword() {
        loginSteps.openLoginPage();
        loginSteps.login("user@mail.com", "");
        // Проверка сообщения об ошибке
    }

    @Test(groups = {"regression"})
    public void testLoginWithEmptyEmail() {
        loginSteps.openLoginPage();
        loginSteps.login("", "Password123");
        // Проверка сообщения об ошибке
    }

    @Test(groups = {"regression"})
    public void testPasswordReminder() {
        loginSteps.openLoginPage();
        loginSteps.clickPasswordReminder();
        // Проверка перехода на страницу восстановления
    }
}

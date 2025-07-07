package steps;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import pages.LoginPage;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    @Step("Открыть страницу авторизации")
    public void openLoginPage() {
        loginPage.openLoginPage();
    }

    @Step("Выполнить вход: email = {email}, пароль = {password}")
    public void login(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Step("Нажать на ссылку восстановления пароля")
    public void clickPasswordReminder() {
        loginPage.clickPasswordReminder();
    }

    @Step("Перейти на страницу регистрации")
    public void goToRegistration() {
        loginPage.clickRegistrationLink();
    }

    @Step("Обновить страницу")
    public void refreshPage() {
        Selenide.refresh();
    }
}

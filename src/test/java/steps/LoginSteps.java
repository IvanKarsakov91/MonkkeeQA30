package steps;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import pages.LoginPage;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    @Step("Открыть страницу логина")
    public void openLoginPage() {
        loginPage.openLoginPage();
    }

    @Step("Вводим email: {email} и пароль: {password}")
    public void login(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Step("Обновляем страницу")
    public void refreshPage() {
        Selenide.refresh();
    }
}



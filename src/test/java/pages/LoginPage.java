package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    public SelenideElement emailField = $("#login");
    public SelenideElement passwordField = $("#password");
    public SelenideElement loginButton = $("button[type='submit']");
    public SelenideElement loginErrorBlock = $("body");

    public void openLoginPage() {
        open("https://monkkee.com/app/#/");
    }

    public void enterEmail(String email) {
        emailField.setValue(email);
    }

    public void enterPassword(String password) {
        passwordField.setValue(password);
    }

    public void clickLogin() {
        loginButton.click();
    }
}



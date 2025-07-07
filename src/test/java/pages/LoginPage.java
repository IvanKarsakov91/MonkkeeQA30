package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    public SelenideElement emailField = $("#username");
    public SelenideElement passwordField = $("#password");
    public SelenideElement loginButton = $("button[type='submit']");
    public SelenideElement passwordReminderLink = $("a[href*='password_reminder']");
    public SelenideElement registrationLink = $("a[href*='registration']");

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

    public void clickPasswordReminder() {
        passwordReminderLink.click();
    }

    public void clickRegistrationLink() {
        registrationLink.click();
    }
}

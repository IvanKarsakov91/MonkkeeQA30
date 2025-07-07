package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    public SelenideElement emailField = $("#email");
    public SelenideElement passwordField = $("#password");
    public SelenideElement confirmPasswordField = $("#passwordConfirmation");
    public SelenideElement passwordHintField = $("#passwordHint");
    public SelenideElement privacyCheckbox = $("#privacyPolicy");
    public SelenideElement responsibilityCheckbox = $("#responsibility");
    public SelenideElement okButton = $("button[type='submit']");

    public void enterEmail(String email) {
        emailField.setValue(email);
    }

    public void enterPassword(String password) {
        passwordField.setValue(password);
    }

    public void confirmPassword(String password) {
        confirmPasswordField.setValue(password);
    }

    public void enterPasswordHint(String hint) {
        passwordHintField.setValue(hint);
    }

    public void acceptPrivacyPolicy() {
        privacyCheckbox.click();
    }

    public void acceptResponsibility() {
        responsibilityCheckbox.click();
    }

    public void submitRegistration() {
        okButton.click();
    }
}

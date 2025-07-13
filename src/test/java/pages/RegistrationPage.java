package pages;

import com.codeborne.selenide.SelenideElement;
import elements.Checkbox;

import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    private final SelenideElement emailField = $("#registration_email");
    private final SelenideElement passwordField = $("#registration_plainPassword_first");
    private final SelenideElement confirmPasswordField = $("#registration_plainPassword_second");
    private final SelenideElement passwordHintField = $("#registration_password_hint");
    private final SelenideElement registerButton = $("#registration_submit");

    private final Checkbox termsCheckbox = new Checkbox("#registration_terms_of_use");
    private final Checkbox warningCheckbox = new Checkbox("#registration_lost_password_warning_registered");

    public void enterEmail(String email) {
        emailField.setValue(email);
    }

    public void enterPassword(String password) {
        passwordField.setValue(password);
    }

    public void confirmPassword(String confirmPassword) {
        confirmPasswordField.setValue(confirmPassword);
    }

    public void enterPasswordHint(String hint) {
        passwordHintField.setValue(hint);
    }

    public void acceptTermsOfUse() {
        termsCheckbox.setCheckboxValue(true);
    }

    public void confirmLostPasswordWarning() {
        warningCheckbox.setCheckboxValue(true);
    }

    public SelenideElement getRegisterButton() {
        return registerButton;
    }

    public void submitRegistration() {
        registerButton.scrollTo().click();
    }

    public void checkTerms() {
        termsCheckbox.setCheckboxValue(true);
    }

    public void checkPasswordWarning() {
        warningCheckbox.setCheckboxValue(true);
    }
}












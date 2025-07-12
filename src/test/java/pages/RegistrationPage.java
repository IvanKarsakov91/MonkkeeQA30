package pages;

import com.codeborne.selenide.SelenideElement;
import elements.Checkbox;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class RegistrationPage {

    public SelenideElement emailField = $("#registration_email");
    public SelenideElement passwordField = $("#registration_password_clear");
    public SelenideElement confirmPasswordField = $("#registration_password_clear_confirmation");
    public SelenideElement passwordHintField = $("#registration_password_hint");
    public SelenideElement registerButton = $("#register");

    private final Checkbox termsOfUseCheckbox = new Checkbox("registration_terms_of_use");
    private final Checkbox lostPasswordWarningCheckbox = new Checkbox("registration_lost_password_warning_registered");

    // Заполнение полей
    public void enterEmail(String email) {
        emailField.shouldBe(visible, enabled).scrollTo().setValue(email);
    }

    public void enterPassword(String password) {
        passwordField.shouldBe(visible, enabled).scrollTo().setValue(password);
    }

    public void confirmPassword(String password) {
        confirmPasswordField.shouldBe(visible, enabled).scrollTo().setValue(password);
    }

    public void enterPasswordHint(String hint) {
        passwordHintField.shouldBe(visible, enabled).scrollTo().setValue(hint);
    }

    // Установить чекбоксы
    public void acceptTermsOfUse() {
        termsOfUseCheckbox.setCheckboxValue(true);
    }

    public void confirmLostPasswordWarning() {
        lostPasswordWarningCheckbox.setCheckboxValue(true);
    }

    // Методы, вызываемые напрямую из теста
    public void checkTerms() {
        acceptTermsOfUse();
    }

    public void checkPasswordWarning() {
        confirmLostPasswordWarning();
    }

    public void submitRegistration() {
        registerButton.shouldBe(visible).scrollTo();
        if (registerButton.isEnabled()) {
            executeJavaScript("arguments[0].click();", registerButton);
        } else {
            throw new IllegalStateException("Кнопка регистрации не активна — проверь заполнение формы и чекбоксы.");
        }
    }

    public SelenideElement getRegisterButton() {
        return registerButton;
    }
}












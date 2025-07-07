package steps;

import io.qameta.allure.Step;
import pages.RegistrationPage;

public class RegistrationSteps {

    RegistrationPage registrationPage = new RegistrationPage();

    @Step("Заполнить форму регистрации")
    public void fillRegistrationForm(String email, String password, String hint) {
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.confirmPassword(password);
        registrationPage.enterPasswordHint(hint);
        registrationPage.acceptPrivacyPolicy();
        registrationPage.acceptResponsibility();
    }

    @Step("Отправить форму регистрации")
    public void submitRegistration() {
        registrationPage.submitRegistration();
    }
}

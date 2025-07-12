package steps;

import com.codeborne.selenide.Selenide;
import models.User;
import pages.RegistrationPage;

public class RegistrationSteps {

    private final RegistrationPage registrationPage = new RegistrationPage();

    public void registerUserWithValidData(String url, User user) {
        Selenide.open(url);
        registrationPage.enterEmail(user.getEmail());
        registrationPage.enterPassword(user.getPassword());
        registrationPage.confirmPassword(user.getPasswordConfirmation());
        registrationPage.enterPasswordHint(user.getPasswordHint());

        registrationPage.acceptTermsOfUse();
        registrationPage.confirmLostPasswordWarning();

        registrationPage.submitRegistration();
    }

    public void registerUserWithEmptyFields(String url, User user) {
        Selenide.open(url);
        registrationPage.enterEmail(user.getEmail());
        registrationPage.enterPassword(user.getPassword());
        registrationPage.confirmPassword(user.getPasswordConfirmation());
        registrationPage.enterPasswordHint(user.getPasswordHint());

        registrationPage.acceptTermsOfUse();
        registrationPage.confirmLostPasswordWarning();

        registrationPage.submitRegistration();
    }

    public void registerUserWithoutConfirmation(String url, User user) {
        Selenide.open(url);
        registrationPage.enterEmail(user.getEmail());
        registrationPage.enterPassword(user.getPassword());
        registrationPage.enterPasswordHint(user.getPasswordHint());

        registrationPage.acceptTermsOfUse();
        registrationPage.confirmLostPasswordWarning();

        registrationPage.submitRegistration();
    }
}
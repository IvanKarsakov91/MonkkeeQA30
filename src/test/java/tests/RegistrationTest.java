package tests;

import org.testng.annotations.Test;
import steps.RegistrationSteps;

public class RegistrationTest {

    RegistrationSteps registrationSteps = new RegistrationSteps();

    @Test(groups = {"smoke"})
    public void testSuccessfulRegistration() {
        registrationSteps.fillRegistrationForm("newuser@mail.com", "Password123", "Hint123");
        registrationSteps.submitRegistration();
        // Проверка сообщения: "User registered"
    }

    @Test(groups = {"regression"})
    public void testEmptyRegistrationFields() {
        registrationSteps.fillRegistrationForm("", "", "");
        registrationSteps.submitRegistration();
        // Проверка ошибки
    }

    @Test(groups = {"regression"})
    public void testMissingPasswordConfirmation() {
        registrationSteps.fillRegistrationForm("user@mail.com", "Password123", "Hint123");
        // Пропустить confirmPassword
        registrationSteps.submitRegistration();
        // Проверка ошибки
    }
}

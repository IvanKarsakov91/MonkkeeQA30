package tests;

import factories.UserFactory;
import io.qameta.allure.*;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;

@Epic("3. Регистрация нового пользователя")
@Feature("3.0. Проверка формы регистрации")
public class RegistrationTest extends BaseTest {

    private final RegistrationPage registrationPage = new RegistrationPage();

    @Test(
            groups = {"smoke"},
            description = "3.1. Успешная регистрация с валидными данными"
    )
    @Story("3.1. Валидная регистрация")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulRegistration() {
        User user = UserFactory.validUser();

        registrationPage.openRegistrationPage();
        registrationPage.registerWithValidData(user);

        Assert.assertTrue(registrationPage.isUserRegisteredMessageVisible(),
                "Сообщение 'User registered' не отображается");
    }

    @Test(
            groups = {"regression"},
            description = "3.2. Регистрация без подтверждения пароля"
    )
    @Story("3.2. Несовпадающие пароли")
    @Severity(SeverityLevel.NORMAL)
    public void testRegistrationWithoutPasswordConfirmation() {
        User user = UserFactory.userWithoutConfirmation();

        registrationPage.openRegistrationPage();
        registrationPage.registerWithoutConfirmation(user);

        Assert.assertTrue(registrationPage.hasPasswordConfirmationError(),
                "Ошибка несоответствия пароля не отображается");
    }

    @Test(
            groups = {"regression"},
            description = "3.3. Кнопка отключена при незаполненной форме"
    )
    @Story("3.3. Пустая форма регистрации")
    @Severity(SeverityLevel.MINOR)
    public void testDisabledButtonOnEmptyForm() {
        registrationPage.openRegistrationPage();
        registrationPage.checkOnlyCheckboxes();

        Assert.assertTrue(registrationPage.isRegisterButtonDisabled(),
                "Кнопка регистрации должна быть неактивна при незаполненной форме");
    }
}


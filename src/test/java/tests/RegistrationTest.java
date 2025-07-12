package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import models.User;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import steps.RegistrationSteps;
import utils.ConfigReader;
import factories.UserFactory;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Epic("1. Регистрация пользователя")
@Feature("1.0. Проверка формы регистрации")
public class RegistrationTest extends BaseTest {

    RegistrationSteps registrationSteps = new RegistrationSteps();
    RegistrationPage registrationPage = new RegistrationPage();

    @Test(
            groups = {"smoke"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "1.1. Успешная регистрация с валидными данными"
    )
    @Story("1.1. Валидные данные")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulRegistration() {
        User user = UserFactory.validUser();
        registrationSteps.registerUserWithValidData(ConfigReader.get("registrationUrl"), user);

        // Проверка: редирект на страницу записей
        assert WebDriverRunner.url().contains("/entries");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "1.2. Пустые все поля формы регистрации"
    )
    @Story("1.2. Пустая форма")
    @Severity(SeverityLevel.NORMAL)
    public void testRegistrationWithEmptyFields() {
        open(ConfigReader.get("registrationUrl"));
        registrationPage.checkTerms();
        registrationPage.checkPasswordWarning();

        registrationPage.getRegisterButton().shouldBe(visible).shouldBe(disabled);
        assert WebDriverRunner.url().contains("/account/registration");
    }

    @Test(
            groups = {"regression"},
            retryAnalyzer = listeners.RetryAnalyzer.class,
            description = "1.3. Отсутствие подтверждения пароля"
    )
    @Story("1.3. Нет подтверждения")
    @Severity(SeverityLevel.NORMAL)
    public void testMissingPasswordConfirmation() {
        User user = UserFactory.userWithoutConfirmation();
        registrationSteps.registerUserWithoutConfirmation(ConfigReader.get("registrationUrl"), user);

        $("body").shouldHave(text("Password confirmation doesn’t match"));
    }
}

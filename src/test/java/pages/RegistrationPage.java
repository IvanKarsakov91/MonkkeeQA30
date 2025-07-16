package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import elements.Checkbox;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    private static final Logger log = LogManager.getLogger(RegistrationPage.class);
    private static final String registrationUrl = "https://monkkee.com/account/registration";

    private final SelenideElement emailField = $("#registration_email");
    private final SelenideElement passwordField = $("#registration_password_clear");
    private final SelenideElement confirmPasswordField = $("#registration_password_clear_confirmation");
    private final SelenideElement passwordHintField = $("#registration_password_hint");
    private final SelenideElement registerButton = $("#register");
    private final SelenideElement registrationBody = $("body");

    private final Checkbox termsCheckbox = new Checkbox("#registration_terms_of_use");
    private final Checkbox warningCheckbox = new Checkbox("#registration_lost_password_warning_registered");

    @Step("Открываем страницу регистрации")
    public void openRegistrationPage() {
        open(registrationUrl);
        log.info("Открыта страница регистрации: {}", registrationUrl);
        Allure.addAttachment("Registration URL", registrationUrl);
    }

    @Step("Регистрируем пользователя с валидными данными")
    public void registerWithValidData(User user) {
        emailField.setValue(user.getEmail());
        passwordField.setValue(user.getPassword());
        confirmPasswordField.setValue(user.getPasswordConfirmation());
        passwordHintField.setValue(user.getPasswordHint());
        termsCheckbox.setCheckboxValue(true);
        warningCheckbox.setCheckboxValue(true);

        registerButton.shouldBe(visible, enabled);
        executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", registerButton);
        sleep(300);
        registerButton.click();

        log.info("Форма отправлена для: {}", user.getEmail());
        Allure.addAttachment("Отправка формы", user.getEmail());

        waitForRegisteredPage();
    }

    @Step("Регистрируем пользователя без подтверждения пароля")
    public void registerWithoutConfirmation(User user) {
        emailField.setValue(user.getEmail());
        passwordField.setValue(user.getPassword());
        passwordHintField.setValue(user.getPasswordHint());
        termsCheckbox.setCheckboxValue(true);
        warningCheckbox.setCheckboxValue(true);

        registerButton.shouldBe(visible, enabled);
        executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", registerButton);
        sleep(300);
        registerButton.click();

        log.info("Форма без подтверждения пароля отправлена для: {}", user.getEmail());
        Allure.addAttachment("Без подтверждения", user.getEmail());
    }

    @Step("Ожидаем переход на страницу /account/registered")
    public void waitForRegisteredPage() {
        for (int i = 0; i < 20; i++) {
            if (WebDriverRunner.url().contains("/account/registered")) {
                log.info("Переход на /account/registered подтверждён");
                Allure.addAttachment("URL подтверждён", WebDriverRunner.url());
                return;
            }
            sleep(250);
        }
        log.warn("Не удалось обнаружить редирект на /account/registered");
    }

    @Step("Выставляем только чекбоксы")
    public void checkOnlyCheckboxes() {
        termsCheckbox.setCheckboxValue(true);
        warningCheckbox.setCheckboxValue(true);
        log.info("Чекбоксы выставлены");
        Allure.addAttachment("Чекбоксы", "Terms + Warning");
    }

    @Step("Проверка: кнопка регистрации должна быть отключена")
    public boolean isRegisterButtonDisabled() {
        boolean result = registerButton.is(disabled);
        log.info("Кнопка регистрации отключена: {}", result);
        return result;
    }

    @Step("Проверка: отображается ошибка несоответствия пароля")
    public boolean hasPasswordConfirmationError() {
        boolean result = registrationBody.shouldHave(text("Password confirmation doesn’t match")).exists();
        log.info("Ошибка подтверждения пароля отображена: {}", result);
        return result;
    }

    @Step("Проверка: отображается сообщение 'User registered'")
    public boolean isUserRegisteredMessageVisible() {
        boolean result = registrationBody.shouldHave(text("User registered")).exists();
        log.info("Сообщение 'User registered' найдено: {}", result);
        Allure.addAttachment("Подтверждение регистрации", "User registered");
        return result;
    }
}


package factories;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserFactory {

    private static final Faker faker = new Faker();
    private static final Logger log = LogManager.getLogger(UserFactory.class);

    @Step("Генерация валидного пользователя")
    public static User validUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        String hint = faker.lorem().word();

        User user = new User(email, password, password, hint);
        log.info("Сгенерирован валидный пользователь: {}", email);
        Allure.addAttachment("Пользователь", email);
        return user;
    }

    @Step("Генерация пустого пользователя")
    public static User emptyUser() {
        User user = new User("", "", "", "");
        log.info("Сгенерирован пустой пользователь");
        Allure.addAttachment("Пользователь", "Пустой");
        return user;
    }

    @Step("Генерация пользователя без подтверждения пароля")
    public static User userWithoutConfirmation() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        String hint = faker.lorem().word();

        User user = new User(email, password, "", hint);
        log.info("Сгенерирован пользователь без подтверждения пароля: {}", email);
        Allure.addAttachment("Пользователь", email);
        return user;
    }

    @Step("Подключение существующего пользователя")
    public static User existingUser() {
        // ⚠️ Убедись, что такой пользователь реально существует в Monkkee
        String email = "ivan.test.qa@example.com";
        String password = "SecurePassword123!";
        String hint = "Мой боевой пароль";

        User user = new User(email, password, password, hint);
        log.info("Используется заранее зарегистрированный пользователь: {}", email);
        Allure.addAttachment("Существующий пользователь", email);
        return user;
    }
}



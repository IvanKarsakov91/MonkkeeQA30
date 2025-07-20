package factories;

import com.github.javafaker.Faker;
import utils.ConfigReader;
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
        return user;
    }

    @Step("Генерация пустого пользователя")
    public static User emptyUser() {
        User user = new User("", "", "", "");
        log.info("Сгенерирован пустой пользователь");
        return user;
    }

    @Step("Генерация пользователя без подтверждения пароля")
    public static User userWithoutConfirmation() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        String hint = faker.lorem().word();

        User user = new User(email, password, "", hint);
        log.info("Сгенерирован пользователь без подтверждения: {}", email);
        return user;
    }

    @Step("Подключение существующего пользователя из config.properties")
    public static User existingUser() {
        String email = ConfigReader.get("user");
        String password = ConfigReader.get("password");
        String confirmation = ConfigReader.get("passwordConfirmation");
        String hint = ConfigReader.get("passwordHint", "Пароль записан");

        User user = new User(email, password, confirmation, hint);
        log.info("Используется пользователь из config.properties: {}", email);
        return user;
    }
}


package factories;

import com.github.javafaker.Faker;
import models.User;

public class UserFactory {

    private static final Faker faker = new Faker();

    public static User validUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        String hint = faker.lorem().word();
        return new User(email, password, password, hint);
    }

    public static User emptyUser() {
        return new User("", "", "", "");
    }

    public static User userWithoutConfirmation() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        String hint = faker.lorem().word();
        return new User(email, password, "", hint);
    }
}

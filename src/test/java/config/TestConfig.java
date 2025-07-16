package config;

import java.util.Properties;
import java.io.InputStream;

public class TestConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String getUserEmail() {
        return get("user");
    }

    public static String getUserPassword() { // 🔹 Метод, которого не хватало
        return get("password");
    }

    public static String getPasswordConfirmation() {
        return get("passwordConfirmation");
    }

    public static String getPasswordHint() {
        return get("passwordHint") != null ? get("passwordHint") : "пароль записан";
    }

    public static String getLoginUrl() {
        return get("loginUrl");
    }

    public static String getStartUrl() {
        return get("startUrl");
    }

    public static String getBrowser() {
        return get("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless", "false"));
    }

    private static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}


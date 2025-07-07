package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

public class WaitUtils {

    public static void waitUntilVisible(SelenideElement element, int timeoutMs) {
        element.shouldBe(Condition.visible, Duration.ofMillis(timeoutMs));
    }

    public static void waitUntilEnabled(SelenideElement element) {
        element.shouldBe(Condition.enabled);
    }
}


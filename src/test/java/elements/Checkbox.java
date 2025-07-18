package elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class Checkbox {

    private static final Logger log = LogManager.getLogger(Checkbox.class);
    private final String selector;

    public Checkbox(String selector) {
        this.selector = selector;
    }

    @Step("Установить чекбокс: {selected} по селектору [{selector}]")
    public void setCheckboxValue(boolean selected) {
        SelenideElement checkbox = $(selector).shouldBe(visible, enabled).scrollTo();
        boolean isChecked = checkbox.isSelected();

        log.info("Чекбокс найден: {} → текущее значение: {}", selector, isChecked);

        if (selected != isChecked) {
            executeJavaScript("arguments[0].click();", checkbox);
            log.info("Чекбокс переключён на: {}", selected);
        } else {
            log.info("Чекбокс уже в нужном состоянии: {}", selected);
        }
    }

    @Step("Принять условия использования")
    public void acceptTermsOfUse() {
        log.info("Принимаем условия использования");
        setCheckboxValue(true);
    }

    @Step("Подтвердить предупреждение о потерянном пароле")
    public void acknowledgeLostPasswordWarning() {
        log.info("Подтверждаем предупреждение о потере пароля");
        setCheckboxValue(true);
    }
}


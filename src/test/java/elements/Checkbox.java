package elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class Checkbox {

    private final String selector;

    public Checkbox(String selector) {
        this.selector = selector;
    }

    @Step("Установить чекбокс: {selected} по селектору [{selector}]")
    public void setCheckboxValue(boolean selected) {
        SelenideElement checkbox = $(selector).shouldBe(visible, enabled).scrollTo();
        boolean isChecked = checkbox.isSelected();

        System.out.println("🔘 Чекбокс найден: " + selector + " → текущее значение: " + isChecked);

        if (selected != isChecked) {
            executeJavaScript("arguments[0].click();", checkbox);
            System.out.println(" Чекбокс переключён на: " + selected);
        } else {
            System.out.println("ℹ Чекбокс уже в нужном состоянии: " + selected);
        }
    }

    @Step("Принять условия использования")
    public void acceptTermsOfUse() {
        System.out.println(" Принимаем условия использования");
        setCheckboxValue(true);
    }

    @Step("Подтвердить предупреждение о потерянном пароле")
    public void acknowledgeLostPasswordWarning() {
        System.out.println(" Подтверждаем предупреждение о потере пароля");
        setCheckboxValue(true);
    }
}


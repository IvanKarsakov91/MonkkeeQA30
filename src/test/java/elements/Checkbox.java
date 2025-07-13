package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class Checkbox {

    private final String selector;

    public Checkbox(String selector) {
        this.selector = selector;
    }

    public void setCheckboxValue(boolean selected) {
        SelenideElement checkbox = $(selector).shouldBe(visible, enabled).scrollTo();
        boolean isChecked = checkbox.isSelected();
        if (selected != isChecked) {
            executeJavaScript("arguments[0].click();", checkbox);
        }
    }

    public void acceptTermsOfUse() {
        setCheckboxValue(true);
    }

    public void acknowledgeLostPasswordWarning() {
        setCheckboxValue(true);
    }
}




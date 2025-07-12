package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class Checkbox {

    private final String id;

    public Checkbox(String id) {
        this.id = id;
    }

    public void setCheckboxValue(boolean selected) {
        SelenideElement checkbox = $("#" + id).shouldBe(visible, enabled).scrollTo();
        boolean isChecked = checkbox.isSelected();

        if (selected && !isChecked) {
            executeJavaScript("arguments[0].click();", checkbox);
        } else if (!selected && isChecked) {
            executeJavaScript("arguments[0].click();", checkbox);
        }
    }
}


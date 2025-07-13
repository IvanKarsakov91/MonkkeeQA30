package steps;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchSteps {

    public void createEntryWithText(String text) {
        $("#create-entry").shouldBe(visible, enabled).click();

        // Редактор — div с contenteditable
        $("div[contenteditable='true']").shouldBe(visible, enabled).click();
        $("div[contenteditable='true']").sendKeys(text);

        $("#back-to-overview").shouldBe(visible, enabled).click();
    }

    public void searchForEntry(String query) {
        $("#appendedInputButton").shouldBe(visible, enabled).setValue(query).pressEnter();
    }

    public void verifyEntryVisible(String text) {
        $$("a.entries__entry").findBy(text(text)).shouldBe(visible);
    }

    public void verifyNoEntriesMessageShown() {
        $("div.entries__no-entries").shouldBe(visible).shouldHave(text("No entries found"));
    }
}



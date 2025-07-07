package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class EntryPage {

    public SelenideElement createEntryButton = $("button[ng-click='createEntry()']");
    public SelenideElement entryTextArea = $("#entryText");
    public SelenideElement saveButton = $("button[ng-click='saveEntry()']");
    public SelenideElement deleteButton = $("button[ng-click='deleteEntry()']");
    public SelenideElement confirmDeleteButton = $("button.confirm");

    public void clickCreateEntry() {
        createEntryButton.click();
    }

    public void enterText(String text) {
        entryTextArea.setValue(text);
    }

    public void saveEntry() {
        saveButton.click();
    }

    public void deleteEntry() {
        deleteButton.click();
        confirmDeleteButton.click();
    }
}

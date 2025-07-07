package steps;

import io.qameta.allure.Step;
import pages.EntryPage;

public class EntrySteps {

    EntryPage entryPage = new EntryPage();

    @Step("Создать новую запись")
    public void createEntry(String text) {
        entryPage.clickCreateEntry();
        entryPage.enterText(text);
        entryPage.saveEntry();
    }

    @Step("Удалить одну запись")
    public void deleteEntry() {
        entryPage.deleteEntry();
    }
}

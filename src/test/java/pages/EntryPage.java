package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class EntryPage {

    private static final Logger log = LogManager.getLogger(EntryPage.class);
    private final PostLoginPage postLoginPage = new PostLoginPage();

    private final SelenideElement createEntryButton = $("#create-entry");
    private final SelenideElement entryEditor = $("div.editor");
    private final SelenideElement saveButton = $("button[ng-click='saveEntry()']");
    private final SelenideElement overviewButton = $("#back-to-overview");
    private final SelenideElement selectFirstCheckbox = $("input[ng-model='model.checked[entry.id]']");
    private final SelenideElement selectAllCheckbox = $("input[ng-model='model.allChecked']");
    private final SelenideElement deleteButton = $("#delete-entries");
    private final SelenideElement confirmDeleteButton = $("button[ng-click='confirmDeleteEntries()']");
    private final ElementsCollection entryLinks = $$("a.entries__entry");

    @Step("Открываем страницу записей через PostLoginPage")
    public void goToEntriesPage() {
        postLoginPage.goToEntries();
        createEntryButton.should(appear, Duration.ofSeconds(10));
        log.info("Переход к записям выполнен");
    }

    @Step("Создать новую запись с текстом: {text}")
    public void createEntry(String text) {
        createEntryButton.should(appear, Duration.ofSeconds(10)).click();
        entryEditor.should(appear, Duration.ofSeconds(10)).setValue(text);
        saveButton.should(appear, Duration.ofSeconds(10)).click();
        log.info("Создана запись с текстом: {}", text);
        Allure.addAttachment("Текст записи", text);
    }

    @Step("Перейти в обзор записей")
    public void backToOverview() {
        overviewButton.should(appear, Duration.ofSeconds(10)).click();
        log.info("Возврат к списку записей");
    }

    @Step("Удалить одну запись")
    public void deleteOneEntry() {
        selectFirstCheckbox.should(appear, Duration.ofSeconds(10)).click();
        deleteButton.should(appear, Duration.ofSeconds(10)).click();
        confirmDeleteButton.should(appear, Duration.ofSeconds(10)).click();
        log.info("Удалена одна запись");
        Allure.addAttachment("Удаление", "Удалена одна запись");
    }

    @Step("Удалить все записи")
    public void deleteAllEntries() {
        selectAllCheckbox.should(appear, Duration.ofSeconds(10)).click();
        deleteButton.should(appear, Duration.ofSeconds(10)).click();
        confirmDeleteButton.should(appear, Duration.ofSeconds(10)).click();
        log.info("Удалены все записи");
        Allure.addAttachment("Удаление", "Удалены все записи");
    }

    @Step("Проверка количества записей на странице")
    public int getEntryCount() {
        int count = entryLinks.size();
        log.info("Количество записей: {}", count);
        Allure.addAttachment("Количество записей", String.valueOf(count));
        return count;
    }
}







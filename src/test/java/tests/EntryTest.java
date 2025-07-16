package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.EntryPage;

@Epic("3. Работа с записями")
@Feature("3.0 Управление записями")
public class EntryTest extends BaseTest {

    private final EntryPage entryPage = new EntryPage();

    @Test(priority = 1, groups = {"smoke"}, description = "3.1. Создание записи с чекбоксом как апрув")
    @Story("3.1. Чекбокс апрув")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateEntryWithCheckbox() {
        entryPage.goToEntriesPage();
        entryPage.createEntry();
        entryPage.selectFirstEntry();
        entryPage.waitForEntriesToAppear(1);

        assert entryPage.getEntryCount() >= 1 : "Запись не появилась";
        entryPage.confirmTestSuccess();
    }

    @Test(priority = 2, groups = {"regression"}, description = "3.2. Удаление одной записи после создания")
    @Story("3.2. Создать и удалить одну")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteEntry() {
        entryPage.goToEntriesPage();
        entryPage.createEntry();
        entryPage.deleteEntryWithSystemAlert();
        entryPage.waitForAllEntriesToDisappear();

        assert entryPage.getEntryCount() == 0 : "Запись не была удалена";
        entryPage.confirmTestSuccess();
    }

    @Test(priority = 3, groups = {"regression"}, description = "3.3. Удаление всех записей")
    @Story("3.3. Удаление всех")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAllEntries() {
        entryPage.goToEntriesPage();

        int attempts = 0;
        while (entryPage.getEntryCount() < 2 && attempts < 2) {
            entryPage.createEntry();
            entryPage.waitForEntriesToAppear(1);
            attempts++;
        }

        entryPage.deleteAllEntries();
        entryPage.waitForAllEntriesToDisappear();

        assert entryPage.getEntryCount() == 0 : "Не все записи были удалены";
        entryPage.confirmTestSuccess();
    }
}


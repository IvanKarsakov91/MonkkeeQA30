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
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());

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
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());

        entryPage.goToEntriesPage();
        entryPage.createEntry();
        entryPage.waitForEntriesToAppear(1);

        int beforeCount = entryPage.getEntryCount();
        entryPage.deleteEntryWithSystemAlert();
        entryPage.waitForEntriesToReduceByOne(beforeCount);

        assert entryPage.getEntryCount() == beforeCount - 1 : "Запись не была удалена";
        entryPage.confirmTestSuccess();
    }

    @Test(priority = 3, groups = {"regression"}, description = "3.3. Удаление всех записей")
    @Story("3.3. Удаление всех")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAllEntries() {
        loginPage.openLoginPage();
        loginPage.login(defaultUser.getEmail(), defaultUser.getPassword());

        entryPage.goToEntriesPage();
        entryPage.ensureMinimumEntries(2);
        entryPage.deleteAllEntries();
        entryPage.waitForAllEntriesToDisappear();

        assert entryPage.getEntryCount() == 0 : "Не все записи были удалены";
        entryPage.confirmTestSuccess();
    }
}



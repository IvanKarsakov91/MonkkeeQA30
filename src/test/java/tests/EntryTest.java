package tests;

import factories.UserFactory;
import io.qameta.allure.*;
import models.User;
import org.testng.annotations.Test;
import pages.EntryPage;
import pages.LoginPage;

@Epic("3. Работа с записями")
@Feature("3.0 Управление записями")
public class EntryTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();
    private final EntryPage entryPage = new EntryPage();

    @Test(priority = 1, groups = {"smoke"}, description = "3.1. Проверка создания новой записи")
    @Story("3.1. Создание")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateEntry() {
        loginPage.loginWithValidUser(UserFactory.existingUser());
        entryPage.goToEntriesPage();

        entryPage.createEntry("No content");
        entryPage.backToOverview();

        assert entryPage.getEntryCount() >= 1 : "Запись не создана";
    }

    @Test(priority = 2, groups = {"regression"}, description = "3.2. Удаление одной записи")
    @Story("3.2. Удаление одной")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteEntry() {
        loginPage.loginWithValidUser(UserFactory.existingUser());
        entryPage.goToEntriesPage();

        entryPage.createEntry("Удалить одну");
        entryPage.backToOverview();

        assert entryPage.getEntryCount() >= 1 : "Нет записей для удаления";

        entryPage.deleteOneEntry();
        assert entryPage.getEntryCount() == 0 : "Запись не была удалена";
    }

    @Test(priority = 3, groups = {"regression"}, description = "3.3. Удаление всех записей")
    @Story("3.3. Удаление всех")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAllEntries() {
        loginPage.loginWithValidUser(UserFactory.existingUser());
        entryPage.goToEntriesPage();

        if (entryPage.getEntryCount() < 2) {
            entryPage.createEntry("Первая");
            entryPage.backToOverview();
            entryPage.createEntry("Вторая");
            entryPage.backToOverview();
        }

        entryPage.deleteAllEntries();
        assert entryPage.getEntryCount() == 0 : "Не все записи были удалены";
    }
}





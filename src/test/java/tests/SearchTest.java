package tests;

import io.qameta.allure.*;
import models.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SearchPage;
import factories.UserFactory;

@Epic("4. Поиск")
@Feature("4.0 Поиск записей")
public class SearchTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();
    private final SearchPage searchPage = new SearchPage();

    @BeforeMethod
    public void setup() {
        User user = UserFactory.existingUser();
        loginPage.loginWithValidUser(user);
    }

    @Test(priority = 1, groups = {"smoke"}, description = "4.1. Поиск записи по запросу '1111'")
    @Story("4.1 Поиск по '1111'")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchEntryWith1111() {
        String searchTerm = "1111";

        searchPage.createEntry(searchTerm);              // 📝 создаём запись
        searchPage.search(searchTerm);                   // 🔍 ищем по запросу
        searchPage.waitForSearchResults(1);              // ⏳ ждём появления результатов

        assert searchPage.isEntryFound(searchTerm) :     // ✅ проверка наличия
                "Запись '" + searchTerm + "' не найдена после поиска";
    }

    @Test(priority = 2, groups = {"regression"}, description = "4.2. Поиск по несуществующему запросу")
    @Story("4.2 Несуществующий запрос")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchNonExistingEntry() {
        String invalidQuery = "non_existing_" + System.currentTimeMillis();

        searchPage.search(invalidQuery);                 // 🔍 ищем по некорректному запросу
        assert searchPage.isNoResultsShown() :           // ✅ проверка блока 'No entries found'
                "Сообщение о пустом результате не отображено";
    }
}


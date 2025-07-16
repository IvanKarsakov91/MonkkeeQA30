package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.SearchPage;

@Epic("4. Поиск")
@Feature("4.0 Поиск записей")
public class SearchTest extends BaseTest {

    private final SearchPage searchPage = new SearchPage();

    @Test(priority = 1, groups = {"smoke"}, description = "4.1. Поиск записи по существующему запросу")
    @Story("4.1 Существующий запрос")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchExistingEntry() {
        String searchTerm = "TestSearchableEntry";
        searchPage.createEntry(searchTerm);
        searchPage.search(searchTerm);
        assert searchPage.isEntryFound(searchTerm);
    }

    @Test(priority = 2, groups = {"regression"}, description = "4.2. Поиск по несуществующему запросу")
    @Story("4.2 Несуществующий запрос")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchNonExistingEntry() {
        String invalidQuery = "ksdkfdsjfds";
        searchPage.search(invalidQuery);
        assert searchPage.isNoResultsShown();
    }
}


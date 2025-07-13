package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import steps.SearchSteps;

@Epic("4. Поиск")
@Feature("4.0 Поиск записей")
public class SearchTest extends BaseTest {

    private final SearchSteps searchSteps = new SearchSteps();

    @Test(
            priority = 1,
            groups = {"smoke"},
            description = "4.1. Поиск записи по существующему запросу"
    )
    @Story("4.1 Существующий запрос")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchExistingEntry() {
        String searchTerm = "TestSearchableEntry";

        searchSteps.createEntryWithText(searchTerm);
        searchSteps.searchForEntry(searchTerm);
        searchSteps.verifyEntryVisible(searchTerm);

        System.out.println("✔ Запись найдена: " + searchTerm);
    }

    @Test(
            priority = 2,
            groups = {"regression"},
            description = "4.2. Поиск по несуществующему запросу"
    )
    @Story("4.2 Несуществующий запрос")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchNonExistingEntry() {
        String invalidQuery = "ksdkfdsjfds";

        searchSteps.searchForEntry(invalidQuery);
        searchSteps.verifyNoEntriesMessageShown();

        System.out.println("✔ Записей не найдено, сообщение отображено");
    }
}

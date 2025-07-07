package steps;

import io.qameta.allure.Step;
import pages.SearchPage;

public class SearchSteps {

    SearchPage searchPage = new SearchPage();

    @Step("Ввести поисковый запрос: {query}")
    public void enterSearchQuery(String query) {
        searchPage.enterSearchQuery(query);
    }

    @Step("Нажать кнопку поиска")
    public void clickSearch() {
        searchPage.clickSearch();
    }

    @Step("Проверить наличие результатов поиска")
    public boolean isResultVisible() {
        return searchPage.isResultVisible();
    }
}

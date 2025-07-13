package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchPage {

    private final SelenideElement searchField = $("#searchInput");
    private final SelenideElement searchButton = $("button[ng-click='searchEntries()']");
    private final SelenideElement resultsBlock = $("#searchResults");
    private final SelenideElement noResultsMessage = $("div.entries__none");

    public void setQuery(String query) {
        searchField.shouldBe(visible, enabled).setValue(query);
    }

    public void submitSearch() {
        searchButton.shouldBe(visible, enabled).click();
    }

    public boolean resultsVisible() {
        return resultsBlock.should(appear).isDisplayed();
    }

    public boolean noEntriesVisible() {
        return noResultsMessage.should(appear).isDisplayed();
    }
}

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SearchPage {

    public SelenideElement searchField = $("#searchInput");
    public SelenideElement searchButton = $("button[ng-click='searchEntries()']");
    public SelenideElement resultContainer = $("#searchResults");

    public void enterSearchQuery(String query) {
        searchField.setValue(query);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public boolean isResultVisible() {
        return resultContainer.isDisplayed();
    }
}

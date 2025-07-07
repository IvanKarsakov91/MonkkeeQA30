package tests;

import org.testng.annotations.Test;
import steps.SearchSteps;

public class SearchTest {

    SearchSteps searchSteps = new SearchSteps();

    @Test(groups = {"regression"})
    public void testSearchExistingEntry() {
        searchSteps.enterSearchQuery("Тестовая запись");
        searchSteps.clickSearch();
        assert searchSteps.isResultVisible();
    }

    @Test(groups = {"regression"})
    public void testSearchNonExistingEntry() {
        searchSteps.enterSearchQuery("Несуществующий текст");
        searchSteps.clickSearch();
        assert !searchSteps.isResultVisible();
    }
}

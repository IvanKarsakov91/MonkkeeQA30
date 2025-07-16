package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchPage {

    private static final Logger log = LogManager.getLogger(SearchPage.class);

    private final SelenideElement createEntryButton = $("#create-entry");
    private final SelenideElement editor = $("div[contenteditable='true']");
    private final SelenideElement overviewButton = $("#back-to-overview");

    private final SelenideElement searchInput = $("#appendedInputButton");
    private final SelenideElement searchResults = $("#searchResults");
    private final SelenideElement noResultsBlock = $("div.entries__no-entries");
    private final ElementsCollection entryLinks = $$("a.entries__entry");

    @Step("Создаём запись с текстом: {text}")
    public void createEntry(String text) {
        createEntryButton.shouldBe(visible, enabled).click();
        editor.shouldBe(visible, enabled).click();
        editor.sendKeys(text);
        overviewButton.shouldBe(visible, enabled).click();
        log.info("Создана запись с текстом: {}", text);
        Allure.addAttachment("Созданный текст", text);
    }

    @Step("Ищем запись по запросу: {query}")
    public void search(String query) {
        searchInput.shouldBe(visible, enabled).setValue(query).pressEnter();
        log.info("Выполнен поиск по запросу: {}", query);
        Allure.addAttachment("Поисковый запрос", query);
    }

    @Step("Проверяем, что найдена запись с текстом: {text}")
    public boolean isEntryFound(String text) {
        boolean found = entryLinks.findBy(text(text)).shouldBe(visible).exists();
        log.info("Найдена запись: {} = {}", text, found);
        return found;
    }

    @Step("Проверяем, что отображено сообщение 'No entries found'")
    public boolean isNoResultsShown() {
        boolean shown = noResultsBlock.shouldBe(visible).has(text("No entries found"));
        log.info("Сообщение о пустом результате отображено: {}", shown);
        return shown;
    }
}


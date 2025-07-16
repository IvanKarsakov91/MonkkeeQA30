package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchPage {

    private static final Logger log = LogManager.getLogger(SearchPage.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private final SelenideElement createEntryButton = $("#create-entry");
    private final SelenideElement editor = $("#editable");
    private final SelenideElement overviewButton = $("#back-to-overview");
    private final SelenideElement searchInput = $("#appendedInputButton");
    private final SelenideElement noResultsBlock = $("div.entries__no-entries");
    private final ElementsCollection entryLinks = $$("a.entries__entry");

    @Step("Создаём запись с текстом: {text}")
    public void createEntry(String text) {
        createEntryButton.shouldBe(visible, enabled).click();

        editor.should(visible, Duration.ofSeconds(10));  // ✅ ожидание с таймаутом
        editor.shouldBe(enabled);
        editor.click();

        editor.sendKeys(text);        // ⌨️ имитация ручного ввода
        editor.pressEscape();         // 🔒 фиксируем изменение

        sleep(1000);                  // ⏳ подстраховка для сохранения

        overviewButton.shouldBe(visible, enabled).click();
        log.info("Создана запись с текстом: {}", text);
        Allure.addAttachment("Созданный текст", text);
    }

    @Step("Ищем запись по запросу: {query}")
    public void search(String query) {
        searchInput.shouldBe(visible, enabled).click();
        searchInput.setValue(query).pressEnter();
        log.info("Выполнен поиск по запросу: {}", query);
        Allure.addAttachment("Поисковый запрос", query);
    }

    @Step("Ожидание появления как минимум {minCount} результатов поиска")
    public void waitForSearchResults(int minCount) {
        entryLinks.shouldHave(sizeGreaterThanOrEqual(minCount), TIMEOUT);
        log.info("Ожидание завершено — найдено минимум {} результатов", minCount);
        Allure.addAttachment("Найдено записей", String.valueOf(entryLinks.size()));
    }

    @Step("Проверяем, что найдена запись с текстом: {text}")
    public boolean isEntryFound(String text) {
        waitForSearchResults(1);
        boolean found = entryLinks.asFixedIterable().stream()
                .anyMatch(e -> e.getText().contains(text));
        log.info("Поиск по тексту '{}' — результат: {}", text, found);
        Allure.addAttachment("Результат поиска", "Найдено: " + found);
        return found;
    }

    @Step("Проверяем, что отображено сообщение 'No entries found'")
    public boolean isNoResultsShown() {
        noResultsBlock.shouldBe(visible, TIMEOUT);
        boolean shown = noResultsBlock.has(text("No entries found"));
        log.info("Сообщение 'No entries found' отображено: {}", shown);
        Allure.addAttachment("Пустой результат", "Показано: " + shown);
        return shown;
    }
}

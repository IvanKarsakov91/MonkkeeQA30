package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import com.codeborne.selenide.WebDriverRunner;
import elements.Checkbox;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("3. Работа с записями")
@Feature("3.0 Управление записями")
public class EntryTest extends BaseTest {

    @Test(
            priority = 1,
            groups = {"smoke"},
            description = "3.1. Проверка создания новой записи"
    )
    @Story("3.1. Создание")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateEntry() {
        $("#create-entry").shouldBe(visible, enabled).click();
        $("div.entries__body").shouldHave(text("No content")).click();
        String entryUrl = WebDriverRunner.url();
        assert entryUrl.matches(".*#/entries/\\d+");
        System.out.println("✔ Создана и открыта запись: " + entryUrl);
    }

    @Test(
            priority = 2,
            groups = {"regression"},
            description = "3.2. Удаление одной записи"
    )
    @Story("3.2. Удаление одной")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteEntry() {
        $("#create-entry").shouldBe(visible, enabled).click();
        $("#back-to-overview").shouldBe(visible, enabled).click();

        $$("a.entries__entry").shouldHave(sizeGreaterThanOrEqual(1));
        new Checkbox("input[ng-model='model.checked[entry.id]']").setCheckboxValue(true);
        $$("input[ng-model='model.checked[entry.id]']").filterBy(selected).shouldHave(sizeGreaterThanOrEqual(1));

        SelenideElement deleteBtn = $("#delete-entries");
        deleteBtn.should(appear).shouldBe(enabled);
        executeJavaScript("arguments[0].click();", deleteBtn);

        SelenideElement confirmBtn = $("button[ng-click='confirmDeleteEntries()']");
        if (confirmBtn.exists()) {
            confirmBtn.should(appear).click();
        }

        $$("a.entries__entry").shouldHave(size(0));
        System.out.println("✔ Запись удалена");
    }

    @Test(
            priority = 3,
            groups = {"regression"},
            description = "3.3. Удаление всех записей"
    )
    @Story("3.3. Удаление всех")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAllEntries() {
        if ($$("a.entries__entry").size() < 2) {
            for (int i = 0; i < 2; i++) {
                $("#create-entry").shouldBe(visible, enabled).click();
                $("#back-to-overview").shouldBe(visible, enabled).click();
            }
        }

        $$("a.entries__entry").shouldHave(sizeGreaterThanOrEqual(2));
        new Checkbox("input[ng-model='model.allChecked']").setCheckboxValue(true);
        $$("input[ng-model='model.checked[entry.id]']").filterBy(selected).shouldHave(sizeGreaterThanOrEqual(2));
        sleep(1000);

        $("#delete-entries").should(appear).shouldBe(enabled).click();

        SelenideElement confirmBtn = $("button[ng-click='confirmDeleteEntries()']");
        if (confirmBtn.exists()) {
            confirmBtn.should(appear).click();
        }

        $$("a.entries__entry").shouldHave(size(0));
        System.out.println("✔ Все записи удалены");
    }
}




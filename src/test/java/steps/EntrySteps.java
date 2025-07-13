package steps;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EntrySteps {

    @Step("Создаём новую запись: {text}")
    public void createEntry(String text) {
        $("#create-entry").shouldBe(visible, enabled).click();
        $("div.editor").shouldBe(visible).click();               // активируем редактор
        $("div.editor").setValue(text);                          // вводим текст
        $("button[ng-click='saveEntry()']").shouldBe(visible, enabled).click(); // сохранить
    }

    @Step("Удаляем одну запись")
    public void deleteEntry() {
        $("input[ng-model='model.checked[entry.id]']").shouldBe(visible, enabled).click(); // выбрать одну
        $("button[ng-click='deleteEntries()']").shouldBe(visible, enabled).click();        // удалить
        $("button[ng-click='confirmDeleteEntries()']").shouldBe(visible, enabled).click(); // подтвердить
    }

    @Step("Удаляем все записи")
    public void deleteAllEntries() {
        $("input[ng-model='model.allChecked']").shouldBe(visible, enabled).click();        // выбрать все
        $("button[ng-click='deleteEntries()']").shouldBe(visible, enabled).click();        // удалить
        $("button[ng-click='confirmDeleteEntries()']").shouldBe(visible, enabled).click(); // подтвердить
    }
}




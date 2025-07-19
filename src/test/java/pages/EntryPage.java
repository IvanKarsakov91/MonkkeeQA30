package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class EntryPage {

    private static final Logger log = LogManager.getLogger(EntryPage.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private final SelenideElement createEntryButton = $("#create-entry");
    private final SelenideElement backToOverviewButton = $("#back-to-overview");
    private final SelenideElement selectAllCheckbox = $("input[ng-model='model.allChecked']");
    private final SelenideElement deleteButton = $("#delete-entries");
    private final SelenideElement confirmDeleteButton = $("button[ng-click='confirmDeleteEntries()']");
    private final SelenideElement firstEntryCheckbox = $("input[ng-model='model.checked[entry.id]']");
    private final ElementsCollection entryLinks = $$("a.entries__entry");

    @Step("Перейти на страницу /#/entries с ожиданием UI")
    public void goToEntriesPage() {
        open("https://monkkee.com/app/#/entries");

        for (int i = 0; i < 20; i++) {
            if (getWebDriver().getCurrentUrl().contains("/#/entries")) break;
            sleep(250);
        }

        createEntryButton.should(appear, TIMEOUT);
        log.info("Страница записей загружена");
    }

    @Step("Создать запись и перейти назад")
    public void createEntry() {
        createEntryButton.should(appear, TIMEOUT);

        if (createEntryButton.has(cssClass("disabled"))) {
            log.warn("Кнопка создания отключена");
            Allure.addAttachment("Запись", "Кнопка создания отключена");
            return;
        }

        createEntryButton.click();
        log.info("Клик по кнопке создания");

        backToOverviewButton.should(appear, TIMEOUT).click();
        log.info("Переход обратно на список записей");
        Allure.addAttachment("Навигация", "Вернулись к списку записей");
    }

    @Step("Выбрать первую запись")
    public void selectFirstEntry() {
        firstEntryCheckbox.should(appear, TIMEOUT).click();
        log.info("Чекбокс первой записи выбран");
        Allure.addAttachment("Чекбокс", "Выбран");
    }

    @Step("Удалить одну запись с подтверждением")
    public void deleteEntryWithSystemAlert() {
        int beforeCount = getEntryCount();

        selectFirstEntry();
        deleteButton.should(appear, TIMEOUT).click();

        boolean confirmed = handleConfirmation();
        waitForEntriesToReduceByOne(beforeCount);

        if (confirmed) {
            log.info("Удаление подтверждено и запись удалена");
            Allure.addAttachment("Удаление", "Подтверждение прошло успешно");
        } else {
            log.warn("Подтверждение не сработало, запись могла остаться");
            Allure.addAttachment("Удаление", "Удаление не подтверждено");
        }
    }

    @Step("Удалить все записи с подтверждением")
    public void deleteAllEntries() {
        selectAllCheckbox.should(appear, TIMEOUT).click();
        deleteButton.should(appear, TIMEOUT).click();

        boolean confirmed = handleConfirmation();
        waitForAllEntriesToDisappear();

        if (confirmed) {
            log.info("Удалены все записи");
            Allure.addAttachment("Удаление", "Все записи удалены после подтверждения");
        } else {
            log.warn("Удаление не было подтверждено");
            Allure.addAttachment("Удаление", "Подтверждение удаления не прошло");
        }
    }

    private boolean handleConfirmation() {
        try {
            new WebDriverWait(getWebDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.alertIsPresent());
            switchTo().alert().accept();
            log.info("Системный alert успешно подтверждён");
            Allure.addAttachment("Alert", "Подтверждено через alert");
            return true;
        } catch (TimeoutException e) {
            if (confirmDeleteButton.exists()) {
                confirmDeleteButton.should(appear, TIMEOUT).click();
                log.info("Кнопка confirm нажата вместо alert");
                Allure.addAttachment("Confirm", "Нажата кнопка подтверждения");
                return true;
            } else {
                log.error("Ни alert, ни confirmDeleteButton не доступны");
                Allure.addAttachment("Ошибка", "Не удалось подтвердить удаление");
                return false;
            }
        }
    }

    @Step("Ожидание появления как минимум {minExpected} записей")
    public void waitForEntriesToAppear(int minExpected) {
        entryLinks.shouldHave(sizeGreaterThanOrEqual(minExpected), TIMEOUT);
        log.info("Обнаружено как минимум {} записей", minExpected);
        Allure.addAttachment("Обнаружено записей", String.valueOf(entryLinks.size()));
    }

    @Step("Ожидание полного исчезновения записей")
    public void waitForAllEntriesToDisappear() {
        entryLinks.shouldHave(size(0), TIMEOUT);
        log.info("Все записи исчезли");
        Allure.addAttachment("Состояние записей", "Записей не осталось");
    }

    @Step("Ожидание уменьшения количества записей на одну. Было: {beforeCount}")
    public void waitForEntriesToReduceByOne(int beforeCount) {
        entryLinks.shouldHave(size(beforeCount - 1), TIMEOUT);
        log.info("Количество записей уменьшилось: было {}, стало {}", beforeCount, entryLinks.size());
        Allure.addAttachment("Уменьшение записей", "Было: " + beforeCount + ", Стало: " + entryLinks.size());
    }

    @Step("Получить количество записей")
    public int getEntryCount() {
        entryLinks.shouldHave(sizeGreaterThanOrEqual(0), TIMEOUT);
        int count = entryLinks.size();
        log.info("Записей на странице: {}", count);
        Allure.addAttachment("Количество записей", String.valueOf(count));
        return count;
    }

    @Step("Тест пройден успешно")
    public void confirmTestSuccess() {
        log.info("Все шаги отработали, тест завершён успешно");
        Allure.addAttachment("Статус", "Тест завершён без ошибок");
    }
}



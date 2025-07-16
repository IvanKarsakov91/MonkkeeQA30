package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CalendarPage {

    private static final Logger log = LogManager.getLogger(CalendarPage.class);
    private final String calendarUrl = "https://monkkee.com/app/#/entries";

    private final SelenideElement calendarButton = $("a[href*='calendar']");
    private final SelenideElement dateInputField = $("#datepicker");
    private final SelenideElement calendarPopup = $("table.table-condensed");
    private final SelenideElement entryList = $("#entryList");

    @Step("Открыть страницу с записями")
    public void start() {
        open(calendarUrl);
        log.info("Открыт URL: {}", calendarUrl);
        Allure.addAttachment("Start URL", calendarUrl);
    }

    @Step("Открыть календарь")
    public void openCalendar() {
        calendarButton.shouldBe(visible, enabled).click();
        log.info("Календарь открыт");
    }

    @Step("Выбрать дату: {date}")
    public void selectDate(String date) {
        dateInputField.shouldBe(visible, enabled).click();
        dateInputField.setValue(date);
        log.info("Выбрана дата: {}", date);
        Allure.addAttachment("Selected Date", date);
    }

    @Step("Нажать на день: {dayText} в календаре")
    public void clickCalendarDay(String dayText) {
        calendarPopup.shouldBe(visible);
        calendarPopup.$$("td.day.enabled").findBy(text(dayText)).shouldBe(visible, enabled).click();
        log.info("Клик по дню календаря: {}", dayText);
    }

    @Step("Проверка: есть ли записи за выбранную дату")
    public boolean isEntryVisibleForDate() {
        boolean visible = entryList.exists() && entryList.isDisplayed();
        log.info("Записи за выбранную дату {}: {}", visible ? "найдены" : "не найдены", visible);
        Allure.addAttachment("Entries found", String.valueOf(visible));
        return visible;
    }
}



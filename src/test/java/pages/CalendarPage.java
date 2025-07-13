package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CalendarPage {

    private final SelenideElement calendarButton = $("a[href*='calendar']");
    private final SelenideElement dateInputField = $("#datepicker");
    private final SelenideElement calendarPopup = $("table.table-condensed"); // если используется всплывающее окно
    private final SelenideElement entryList = $("#entryList");

    public void openCalendar() {
        calendarButton.shouldBe(visible, enabled).click();
    }

    public void selectDate(String dateString) {
        dateInputField.shouldBe(visible, enabled).click();
        dateInputField.setValue(dateString); // формат зависит от custom-date-picker: "dd/mm/yyyy"
    }

    public void clickCalendarDay(String dayText) {
        calendarPopup.shouldBe(visible);
        calendarPopup.$$("td.day.enabled").findBy(text(dayText)).shouldBe(visible, enabled).click();
    }

    public boolean isEntryVisibleForDate() {
        return entryList.shouldBe(visible).exists();
    }
}

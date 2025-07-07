package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class CalendarPage {

    public SelenideElement calendarButton = $("a[href*='calendar']");
    public SelenideElement datePicker = $("input[type='date']");
    public SelenideElement entryList = $("#entryList");

    public void openCalendar() {
        calendarButton.click();
    }

    public void selectDate(String date) {
        datePicker.setValue(date); // формат: "2025-07-07"
    }

    public boolean isEntryVisibleForDate() {
        return entryList.isDisplayed();
    }
}

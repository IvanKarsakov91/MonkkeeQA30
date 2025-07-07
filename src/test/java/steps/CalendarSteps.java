package steps;

import io.qameta.allure.Step;
import pages.CalendarPage;

public class CalendarSteps {

    CalendarPage calendarPage = new CalendarPage();

    @Step("Открыть календарь")
    public void openCalendar() {
        calendarPage.openCalendar();
    }

    @Step("Выбрать дату: {date}")
    public void selectDate(String date) {
        calendarPage.selectDate(date);
    }

    @Step("Проверить наличие записей за выбранную дату")
    public boolean isEntryVisibleForDate() {
        return calendarPage.isEntryVisibleForDate();
    }
}

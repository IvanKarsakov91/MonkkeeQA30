package tests;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import steps.CalendarSteps;

public class CalendarTest extends BaseTest {

    private final CalendarSteps calendarSteps = new CalendarSteps();

    @Test(groups = {"regression"})
    public void testEntriesVisibleForSelectedDate() {
        Selenide.open("https://monkkee.com/app/#/entries");
        calendarSteps.openCalendar();
        calendarSteps.selectDate("2025-07-07");
        assert calendarSteps.isEntryVisibleForDate();
    }
}

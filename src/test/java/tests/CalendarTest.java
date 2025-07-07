package tests;

import org.testng.annotations.Test;
import steps.CalendarSteps;

public class CalendarTest {

    CalendarSteps calendarSteps = new CalendarSteps();

    @Test(groups = {"regression"})
    public void testEntriesVisibleForSelectedDate() {
        calendarSteps.openCalendar();
        calendarSteps.selectDate("2025-07-07");
        assert calendarSteps.isEntryVisibleForDate();
    }
}

package tests;

import org.testng.annotations.Test;
import pages.CalendarPage;

public class CalendarTest extends BaseTest {

    private final CalendarPage calendarPage = new CalendarPage();

    @Test(groups = {"regression"})
    public void testEntriesVisibleForSelectedDate() {
        calendarPage.start();
        calendarPage.openCalendar();
        calendarPage.selectDate("07/07/2025");
        assert calendarPage.isEntryVisibleForDate();
    }
}



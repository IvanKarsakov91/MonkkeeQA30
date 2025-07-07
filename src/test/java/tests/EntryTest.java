package tests;

import org.testng.annotations.Test;
import steps.EntrySteps;

public class EntryTest {

    EntrySteps entrySteps = new EntrySteps();

    @Test(groups = {"smoke"})
    public void testCreateEntry() {
        entrySteps.createEntry("Тестовая запись");
        // Проверка, что запись появилась
    }

    @Test(groups = {"regression"})
    public void testDeleteEntry() {
        entrySteps.deleteEntry();
        // Проверка, что запись удалена
    }

    @Test(groups = {"regression"})
    public void testDeleteAllEntries() {
        // Повторить deleteEntry несколько раз
        // Проверка, что список пуст
    }
}

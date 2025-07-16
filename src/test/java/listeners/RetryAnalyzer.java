package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 1;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (attempt < MAX_RETRY) {
                System.out.printf(" Перезапуск теста [%s] | Попытка #%d%n", result.getName(), attempt);
                attempt++;
                return true;
            } else {
                System.out.printf(" Тест [%s] окончательно упал после %d попыток%n", result.getName(), MAX_RETRY);
            }
        }
        return false;
    }
}


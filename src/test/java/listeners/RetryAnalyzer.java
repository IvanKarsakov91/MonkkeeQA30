package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
    private int attempt = 1;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (attempt < MAX_RETRY) {
                log.info("Перезапуск теста [{}] | Попытка #{}", result.getName(), attempt);
                attempt++;
                return true;
            } else {
                log.info("Тест [{}] окончательно упал после {} попыток", result.getName(), MAX_RETRY);
            }
        }
        return false;
    }
}



package listeners;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.printf("🟡 STARTING TEST: %s%n", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.printf("✅ TEST PASSED: %s | Duration: %ds%n", result.getName(), getExecutionTime(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.printf(" TEST FAILED: %s | Duration: %ds%n", result.getName(), getExecutionTime(result));
        WebDriver driver = WebDriverRunner.getWebDriver();

        // 📸 Скриншот
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.getLifecycle().addAttachment("Screenshot", "image/png", "png", screenshot);

        // 📄 HTML исходник
        String pageSource = driver.getPageSource();
        Allure.getLifecycle().addAttachment("Page Source", "text/html", "html", pageSource.getBytes(StandardCharsets.UTF_8));

        // 📍 URL страницы
        Allure.addAttachment("Current URL", driver.getCurrentUrl());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.printf(" TEST SKIPPED: %s%n", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // необязательно
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println(" Test suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(" Test suite finished: " + context.getName());
    }

    private long getExecutionTime(ITestResult result) {
        return TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
    }
}


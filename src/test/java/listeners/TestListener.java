package listeners;

import com.codeborne.selenide.Screenshots;
import io.qameta.allure.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Начало набора тестов: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Завершение набора тестов: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Запуск теста: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Тест успешен: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Тест провален: " + result.getMethod().getMethodName());
        attachScreenshot();
        attachLog(result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Тест пропущен: " + result.getMethod().getMethodName());
    }

    @Attachment(value = "Скриншот при ошибке", type = "image/png")
    public byte[] attachScreenshot() {
        try {
            File screenshot = Screenshots.takeScreenShotAsFile();
            Path path = screenshot.toPath();
            return Files.readAllBytes(path);
        } catch (Exception e) {
            logger.error("Ошибка при создании скриншота: " + e.getMessage());
            return new byte[0];
        }
    }

    @Attachment(value = "Текст ошибки", type = "text/plain")
    public String attachLog(String message) {
        return message;
    }
}


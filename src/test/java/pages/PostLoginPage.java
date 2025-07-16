package pages;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;

public class PostLoginPage {

    private static final Logger log = LogManager.getLogger(PostLoginPage.class);

    @Step("Переход к записям через 'Go to your entries' + фиксируем URL")
    public void goToEntries() {
        SelenideElement link = $("a[href='#/entries']");
        link.should(appear, Duration.ofSeconds(10)).shouldBe(enabled);
        executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
        sleep(300);
        link.click();

        sleep(1000); // дожидаемся редиректа

        open("https://monkkee.com/app/#/entries");
        $("#create-entry").should(appear, Duration.ofSeconds(10));

        log.info("Жёсткий переход на страницу записей завершён");
        Allure.addAttachment("URL после переадресации", WebDriverRunner.url());
    }
}


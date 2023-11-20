package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

@Tag("SelenideStepsTest")
public class SelenideStepsTest extends TestBase {


            private final static String REPOSITORY = "eroshenkoam/allure-example";
            private final static String ISSUE = "Issue_created_to_test_allure_reports";

            @Test
            public void testIssueSearch() {

                Allure.step("Open github mainpage", () -> {
                    open("https://github.com");
                });
                Allure.step("Search repo " + REPOSITORY, () -> {
                    $("[data-target='qbsearch-input.inputButtonText']").click();
                    $("#query-builder-test").setValue(REPOSITORY).pressEnter();
                });
                Allure.step("Click link " + REPOSITORY, () -> {
                    $(linkText(REPOSITORY)).click();
                });
                Allure.step("Click Tab Issues", () -> {
                    $("#issues-tab").click();
                });
                Allure.step("Check that issue  " + ISSUE + " exist", () -> {
                    $(withText(ISSUE)).should(Condition.exist);
                });
            }


}

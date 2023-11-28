package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBaseRemote {
    @BeforeAll
    static void init() {
        String browserFromProperties = System.getProperty("browser", "chrome_100.0");
        String[] browserInfo = browserFromProperties.split("_");
        String browserFromPropertiesName = browserInfo[0];
        String browserFromPropertiesVersion = browserInfo[1];

        browser = browserFromPropertiesName;
        browserVersion = browserFromPropertiesVersion;
        browserSize = System.getProperty("browserSize", "1920x1080");
        baseUrl = System.getProperty("baseUrl", "https://demoqa.com");
        remote = "https://user1:1234@" +
                System.getProperty("remoteWebDriver", "selenoid.autotests.cloud") +
                "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browserFromPropertiesName);
        capabilities.setCapability("browserVersion", browserFromPropertiesVersion);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    public void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addArtefactsToReport() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.addVideo();
        closeWebDriver();
    }
}
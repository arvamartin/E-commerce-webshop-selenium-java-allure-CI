package tests;

import allure.AllureScreenshotListener;
import framework.core.Browser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureScreenshotListener.class)
public abstract class BaseTest {

    @AfterEach
    protected void cleanupDriver() {
        Browser.quitDriver();
    }
}

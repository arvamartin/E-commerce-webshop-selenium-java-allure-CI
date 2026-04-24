package tests;

import allure.AllureScreenshotListener;
import framework.core.Browser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(AllureScreenshotListener.class)
public abstract class BaseTest {

    @AfterEach
    protected void cleanupDriver() {
        Browser.quitDriver();
    }
}

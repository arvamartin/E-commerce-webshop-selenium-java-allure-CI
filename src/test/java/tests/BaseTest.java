package tests;

import allure.AllureScreenshotListener;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(AllureScreenshotListener.class)
public abstract class BaseTest {
}
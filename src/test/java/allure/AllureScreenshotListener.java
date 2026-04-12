package allure;

import framework.core.Browser;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class AllureScreenshotListener implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(
            ExtensionContext context,
            Throwable throwable) {

        WebDriver driver = Browser.getDriver();

        if (driver != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

                attachScreenshot(screenshot);

            } catch (Exception e) {
                System.out.println("Screenshot error: " + e.getMessage());
            }
        }

        throw new RuntimeException(throwable);
    }

    @Attachment(value = "Failed test screenshot", type = "image/png")
    public byte[] attachScreenshot(byte[] screenshot) {
        return screenshot;
    }
}
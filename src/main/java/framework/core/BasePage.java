package framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage() {
        this.driver = Browser.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(resolveTimeoutSeconds()));
        PageFactory.initElements(driver, this);
    }

    private int resolveTimeoutSeconds() {
        String configuredTimeout = PropertyReader.getConfigValue("Config.properties", "timeout");
        if (configuredTimeout == null || configuredTimeout.isBlank()) {
            return DEFAULT_TIMEOUT_SECONDS;
        }

        try {
            return Integer.parseInt(configuredTimeout.trim());
        } catch (NumberFormatException ignored) {
            return DEFAULT_TIMEOUT_SECONDS;
        }
    }
}

package framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private String timeOut = PropertyReader.getConfigValue("Config.properties", "timeout");

    public BasePage() {
        this.driver = Browser.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(timeOut)));
        PageFactory.initElements(driver, this);
    }
}

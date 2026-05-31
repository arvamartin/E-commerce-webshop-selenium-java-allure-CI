package framework.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Element {
    private static final int DEFAULT_TIMEOUT_SECONDS = 5;
    private final WebElement element;
    private final WebDriverWait wait;

    public Element(WebElement webElement) {
        this.element = webElement;
        wait = new WebDriverWait(Browser.getDriver(), Duration.ofSeconds(resolveTimeoutSeconds()));
    }

    public Element waitForClickable() {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public Element waitForVisible() {
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public Element waitForInvisible() {
        wait.until(ExpectedConditions.invisibilityOf(element));
        return this;
    }

    public void click() {
        element.click();
    }

    public void javascriptExecutorClick(WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public Element clearAndType(String text) {
        element.clear();
        element.sendKeys(text);
        return this;
    }

    public String getText(){
        return element.getText();
    }

    public String getAttribute(String attribute) {
        return element.getAttribute(attribute);
    }

    public String getCssValue(String cssProperty) {
        return element.getCssValue(cssProperty);
    }

    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    private int resolveTimeoutSeconds() {
        try {
            String timeoutValue = PropertyReader.getConfigValue("Config.properties", "timeout");
            if (timeoutValue == null || timeoutValue.isBlank()) {
                return DEFAULT_TIMEOUT_SECONDS;
            }
            return Integer.parseInt(timeoutValue.trim());
        } catch (Exception ignored) {
            return DEFAULT_TIMEOUT_SECONDS;
        }
    }
}

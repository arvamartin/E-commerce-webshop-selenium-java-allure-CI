package framework.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Element {
    private final WebElement element;
    private final WebDriverWait wait;

    public Element(WebElement webElement) {
        this.element = webElement;
        wait = new WebDriverWait(Browser.getDriver(), Duration.ofSeconds(2));
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

    public void sendKeys(String text) {
        element.sendKeys(text);
    }

    public String getText(){
        return element.getText();
    }

    public Element assertText(String expectedText){
        assertThat(element.getText(), equalTo(expectedText));
        return this;
    }

    public Element assertCssValue(String cssProperty, String expectedValue) {
        String actualValue = element.getCssValue(cssProperty);
        assertThat(actualValue, equalTo(expectedValue));
        return this;
    }

    public Element assertCssValueContains(String cssProperty, String... expectedParts) {
        String actualValue = element.getCssValue(cssProperty);
        for (String part : expectedParts) {
            assertThat(actualValue, containsString(part));
        }
        return this;
    }

    public Element shouldBeVisible() {
        assertThat(element.isDisplayed(), is(true));
        return this;
    }

    public Element assertAttribute(String attribute, String expectedValue) {
        String actualValue = element.getAttribute(attribute);
        assertThat(actualValue, equalTo(expectedValue));
        return this;
    }

    public Element assertHasTextAndIsVisible(String expectedText) {
        assertThat(element.isDisplayed(), is(true));
        assertThat(element.getText(), equalTo(expectedText)); 
        return this;
    }

    public Element shouldNotBeVisible() {
        assertThat(element.isDisplayed(), is(false));
        return this;
    }
}

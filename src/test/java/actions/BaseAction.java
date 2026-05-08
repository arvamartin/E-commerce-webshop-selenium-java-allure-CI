package actions;

import framework.core.Browser;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public abstract class BaseAction<T extends BaseAction<T>> {
    private static final Duration URL_VALIDATION_TIMEOUT = Duration.ofSeconds(10);

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    @Step("Navigate to: {url}")
    public T navigateTo(String url) {
        Objects.requireNonNull(url, "url must not be null");
        Browser.getDriver().get(url);
        return self();
    }

    @Step("Validate current URL")
    public void validateCurrentPage(String expectedUrl) {
        Objects.requireNonNull(expectedUrl, "expectedUrl must not be null");
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), URL_VALIDATION_TIMEOUT);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = Browser.getDriver().getCurrentUrl();
        assertThat("Unexpected URL", actualUrl, equalTo(expectedUrl));
    }
}

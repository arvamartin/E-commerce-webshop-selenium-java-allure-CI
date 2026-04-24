package actions;

import framework.core.Browser;
import io.qameta.allure.Step;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public abstract class BaseAction<T extends BaseAction<T>> {

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
        String actualUrl = Browser.getDriver().getCurrentUrl();
        assertThat("Unexpected URL", actualUrl, equalTo(expectedUrl));
    }
}

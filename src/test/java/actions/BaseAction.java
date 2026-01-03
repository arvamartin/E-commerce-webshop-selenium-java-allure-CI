package actions;

import framework.core.Browser;
import org.openqa.selenium.WebDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public abstract class BaseAction<T extends BaseAction<T>> {

    private WebDriver driver = Browser.getDriver();

    public T navigateTo(String url) {
        driver.get(url);
        return (T)this;
    }

    public void validateCurrentPage(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();

        assertThat(actualUrl, equalTo(expectedUrl));
    }
}
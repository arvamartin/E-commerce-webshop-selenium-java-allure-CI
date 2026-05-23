package actions;

import framework.core.PropertyReader;
import framework.utils.CssMatchType;
import framework.utils.CssExpectation;
import framework.utils.LoginPanelElementExpected;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.LoginPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static framework.core.Constants.*;

public class LoginAction extends BaseAction<LoginAction> {
    private final LoginPage loginPage;

    public LoginAction() {
        this.loginPage = new LoginPage();
    }

    public void loginAsStandardUser() {
        navigateTo(LOGIN_URL.getValue());
        enterUsername(STANDARD_USER.getValue());
        enterPassword(STANDARD_PASSWORD.getValue());
        clickOnLoginBtn();
        validateCurrentPage(HOME_PAGE_URL.getValue());
    }

    @Step("User enters username")
    public LoginAction enterUsername(String username) {
        loginPage.enterUsername(username);
        return this;
    }

    @Step("User enters password")
    public LoginAction enterPassword(String password) {
        loginPage.enterPassword(password);
        return this;
    }

    @Step("User clicks on login button")
    public LoginAction clickOnLoginBtn() {
        loginPage.clickLoginButton();
        return this;
    }

    @Step("Error message is displayed")
    public LoginAction isErrorPopupDisplayedWithMessage(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorPopupText();
        assertThat(loginPage.isErrorPopupDisplayed(), is(true));
        assertThat(actualErrorMessage, equalTo(expectedErrorMessage));
        return this;
    }


    @Step("Verifies background color")
    public LoginAction verifyBackgroundColor() {
        assertThat(loginPage.getLoginPageContainerCssValue("background-color"), equalTo(loginPageProp("backgroundColor")));
        return this;
    }


    @Step("Verifies login panel elements")
    public LoginAction verifyLoginPanel() {
        for (LoginPanelElementExpected expected : LoginPanelElementExpected.values()) {
            WebElement element = resolveElement(expected);
            loginPage.waitForVisible(element);

            verifyElementTextOrAttribute(element, expected);
            verifyElementCss(element, expected);
        }
        return this;
    }

    private void verifyElementTextOrAttribute(WebElement element, LoginPanelElementExpected expected) {
        switch (expected.getTextAssertionType()) {
            case NONE -> {
                return;
            }
            case PLACEHOLDER -> assertThat(loginPage.getAttribute(element, "placeholder"), equalTo(loginPageProp(expected.getTextAssertionKey())));
            case VALUE -> assertThat(loginPage.getAttribute(element, "value"), equalTo(loginPageProp(expected.getTextAssertionKey())));
            case TEXT -> assertThat(loginPage.getText(element), equalTo(loginPageProp(expected.getTextAssertionKey())));
        }
    }

    private void verifyElementCss(WebElement element, LoginPanelElementExpected expected) {
        for (CssExpectation cssExpectation : expected.getCssExpectations()) {
            List<String> expectedValues = cssExpectation.expectedValueKeys().stream()
                    .map(this::loginPageProp)
                    .toList();

            String actualValue = loginPage.getCssValue(element, cssExpectation.cssProperty());
            if (cssExpectation.matchType() == CssMatchType.CONTAINS_ALL) {
                for (String expectedValue : expectedValues) {
                    assertThat(actualValue, org.hamcrest.Matchers.containsString(expectedValue));
                }
            } else if (cssExpectation.matchType() == CssMatchType.EXACT) {
                assertThat(actualValue, equalTo(expectedValues.get(0)));
            }
        }
    }

    public WebElement resolveElement(LoginPanelElementExpected expected) {
        return loginPage.resolveElement(expected);
    }

    private String loginPageProp(String key) {
        return PropertyReader.getValue("loginUi", "loginPage", key);
    }
}

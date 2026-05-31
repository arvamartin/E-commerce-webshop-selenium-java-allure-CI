package actions;

import framework.core.PropertyReader;
import framework.core.Element;
import framework.utils.CssAssertions;
import framework.utils.CssExpectation;
import framework.utils.LoginPanelElementExpected;
import io.qameta.allure.Step;
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
        CssExpectation expectation = CssExpectation.colorNear("background-color", "backgroundColor");
        CssAssertions.assertCss(
                "background-color",
                loginPage.getLoginPageContainerCssValue("background-color"),
                expectation,
                loginPageProp("backgroundColor")
        );
        return this;
    }


    @Step("Verifies login panel elements")
    public LoginAction verifyLoginPanel() {
        for (LoginPanelElementExpected expected : LoginPanelElementExpected.values()) {
            Element element = resolveElement(expected);
            element.waitForVisible();

            verifyElementTextOrAttribute(element, expected);
            verifyElementCss(element, expected);
        }
        return this;
    }

    private void verifyElementTextOrAttribute(Element element, LoginPanelElementExpected expected) {
        switch (expected.getTextAssertionType()) {
            case NONE -> {
                return;
            }
            case PLACEHOLDER -> assertThat(element.getAttribute("placeholder"), equalTo(loginPageProp(expected.getTextAssertionKey())));
            case VALUE -> assertThat(element.getAttribute("value"), equalTo(loginPageProp(expected.getTextAssertionKey())));
            case TEXT -> assertThat(element.getText(), equalTo(loginPageProp(expected.getTextAssertionKey())));
        }
    }

    private void verifyElementCss(Element element, LoginPanelElementExpected expected) {
        for (CssExpectation cssExpectation : expected.getCssExpectations()) {
            List<String> expectedValues = cssExpectation.expectedValueKeys().stream()
                    .map(this::loginPageProp)
                    .toList();

            CssAssertions.assertCss(
                    cssExpectation.cssProperty(),
                    element.getCssValue(cssExpectation.cssProperty()),
                    cssExpectation,
                    expectedValues.toArray(String[]::new)
            );
        }
    }

    public Element resolveElement(LoginPanelElementExpected expected) {
        return loginPage.resolveElement(expected);
    }

    private String loginPageProp(String key) {
        return PropertyReader.getValue("loginUi", "loginPage", key);
    }
}

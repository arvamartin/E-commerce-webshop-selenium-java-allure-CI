package actions;

import framework.core.Element;
import framework.core.PropertyReader;
import framework.utils.CssMatchType;
import framework.utils.CssExpectation;
import framework.utils.LoginPanelElementExpected;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.LoginPage;

import java.util.List;

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
        new Element(loginPage.getUserNameInput())
                .waitForVisible()
                .clearAndType(username);
        return this;
    }

    @Step("User enters password")
    public LoginAction enterPassword(String password) {
        new Element(loginPage.getPasswordInput())
                .waitForVisible()
                .clearAndType(password);
        return this;
    }

    @Step("User clicks on login button")
    public LoginAction clickOnLoginBtn() {
        new Element(loginPage.getLoginBtn())
                .waitForClickable()
                .click();
        return this;
    }

    @Step("Error message is displayed")
    public LoginAction isErrorPopupDisplayedWithMessage(String expectedErrorMessage) {
        new Element(loginPage.getErrorPopup())
                .waitForVisible()
                .assertHasTextAndIsVisible(expectedErrorMessage);

        return this;
    }


    @Step("Verifies background color")
    public LoginAction verifyBackgroundColor() {
        new Element(loginPage.getLoginPageContainer())
                .assertCssValue("background-color", loginPageProp("backgroundColor"));
        return this;
    }


    @Step("Verifies login panel elements")
    public LoginAction verifyLoginPanel() {
        for (LoginPanelElementExpected expected : LoginPanelElementExpected.values()) {
            Element element = new Element(resolveElement(expected)).waitForVisible();

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
            case PLACEHOLDER -> element.assertAttribute("placeholder", loginPageProp(expected.getTextAssertionKey()));
            case VALUE -> element.assertAttribute("value", loginPageProp(expected.getTextAssertionKey()));
            case TEXT -> element.assertText(loginPageProp(expected.getTextAssertionKey()));
        }
    }

    private void verifyElementCss(Element element, LoginPanelElementExpected expected) {
        for (CssExpectation cssExpectation : expected.getCssExpectations()) {
            List<String> expectedValues = cssExpectation.expectedValueKeys().stream()
                    .map(this::loginPageProp)
                    .toList();

            if (cssExpectation.matchType() == CssMatchType.CONTAINS_ALL) {
                element.assertCssValueContains(cssExpectation.cssProperty(), expectedValues.toArray(new String[0]));
            } else if (cssExpectation.matchType() == CssMatchType.EXACT) {
                element.assertCssValue(cssExpectation.cssProperty(), expectedValues.get(0));
            }
        }
    }

    public WebElement resolveElement(LoginPanelElementExpected expected) {
        return switch (expected) {
            case TITLE -> loginPage.getTitleElement();
            case LOGIN_PANEL -> loginPage.getLoginPanel();
            case USERNAME_INPUT -> loginPage.getUserNameInput();
            case PASSWORD_INPUT -> loginPage.getPasswordInput();
            case LOGIN_BUTTON -> loginPage.getLoginBtn();
        };
    }

    private String loginPageProp(String key) {
        return PropertyReader.getValue("loginUi", "loginPage", key);
    }
}

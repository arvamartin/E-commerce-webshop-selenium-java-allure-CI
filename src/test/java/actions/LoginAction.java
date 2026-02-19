package actions;

import framework.core.Browser;
import framework.core.Element;
import framework.core.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;

import static framework.core.Constants.*;
import static framework.core.Constants.HOME_PAGE_URL;

public class LoginAction extends BaseAction<LoginAction> {
    private final LoginPage loginPage;
    private final WebDriver driver;

    public LoginAction() {
        loginPage = new LoginPage();
        driver = Browser.getDriver();
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
                .sendKeys(username);
        return this;
    }

    @Step("User enters password")
    public LoginAction enterPassword(String password) {
        new Element(loginPage.getPasswordInput())
                .waitForVisible()
                .sendKeys(password);
        return this;
    }

    public LoginAction clickOnLoginBtn() {
        new Element(loginPage.getLoginBtn())
                .waitForVisible()
                .click();
        return this;
    }

    @Step("Error message is displayed")
    public LoginAction isErrorPopupDisplayedWithMessage(String expectedErrorMessage) {
        new Element(loginPage.getErrorPopup())
                .assertHasTextAndIsVisible(expectedErrorMessage);

        return this;
    }

    @Step("Verifies title text")
    public LoginAction verifyTitleText() {
        String expectedTitleText = loginPageProp("titleText");
        String expectedFontSize = loginPageProp("titleTextFontSize");
        String expectedTextColor = loginPageProp("titleTextColor");
        String expectedFontFamily1 = loginPageProp("titleTextFontFamily1");
        String expectedFontFamily2 = loginPageProp("titleTextFontFamily2");

        new Element(loginPage.getTitleElement())
                .assertText(expectedTitleText)
                .assertCssValue("font-size", expectedFontSize)
                .assertCssValue("color", expectedTextColor)
                .assertCssValueContains("font-family", expectedFontFamily1, expectedFontFamily2);

        return this;
    }

    @Step("Verifies background color")
    public LoginAction verifyBackgroundColor() {
        new Element(loginPage.getLoginPageContainer())
                .assertCssValue("background-color", loginPageProp("backgroundColor"));
        return this;
    }

    @Step("Verifies login panel")
    public LoginAction verifyLoginPanel() {
        new Element(loginPage.getLoginPanel())
                .assertCssValue("background-color", loginPageProp("loginPanelColor"))
                .shouldBeVisible();

        new Element(loginPage.getUserNameInput())
                .assertAttribute("placeholder", loginPageProp("usernamePlaceholder"));

        new Element(loginPage.getPasswordInput())
                .assertAttribute("placeholder", loginPageProp("passwordPlaceholder"));

        return this;
    }

    @Step("Verifies login button")
    public void verifyLoginButton() {
        new Element(loginPage.getLoginBtn())
                .shouldBeVisible()
                .assertCssValue("background-color", loginPageProp("loginBtnColor"))
                .assertCssValue("border-radius", loginPageProp("loginBtnBorderRadius"))
                .assertCssValue("color", loginPageProp("loginBtnTextColor"))
                .assertAttribute("value", loginPageProp("loginBtnText"));
    }

    private String loginPageProp(String key) {
        return PropertyReader.getValue("loginUi", "loginPage", key);
    }
}

package actions;

import framework.core.Browser;
import framework.core.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.LoginPage;

import static framework.core.Constants.*;
import static framework.core.Constants.HOME_PAGE_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginAction extends BaseAction<LoginAction>{
    private final LoginPage loginPage;
        private final WebDriver driver;

    public LoginAction() {
        loginPage = new LoginPage();
        driver = Browser.getDriver();
    }

    public void login(){
        navigateTo(LOGIN_URL.getValue());
        enterUsername(STANDARD_USER.getValue());
        enterPassword(STANDARD_PASSWORD.getValue());
        clickOnLoginBtn();
        validateCurrentPage(HOME_PAGE_URL.getValue());
    }


    public LoginAction enterUsername(String username) {
        loginPage.getUserNameInput().sendKeys(username);
        return this;
    }

    public LoginAction enterPassword(String password) {
        loginPage.getPasswordInput().sendKeys(password);
        return this;
    }

    public LoginAction clickOnLoginBtn() {
        loginPage.getLoginBtn().click();
        return this;
    }

    public LoginAction isErrorPopupDisplayedWithMessage(String expectedErrorMessage) {
        WebElement errorPopup = loginPage.getErrorPopup();

        assertThat(errorPopup.isDisplayed(), is(true));
        assertThat(errorPopup.getText(), equalTo(expectedErrorMessage));

        return this;
    }




    public LoginAction verifyTitleText() {
        String expectedTitleText = PropertyReader.getValue("loginPage", "titleText");
        String actualTitleText = loginPage.getTitleElement().getText();

        assertThat(actualTitleText, equalTo(expectedTitleText));

        String expectedFontSize = PropertyReader.getValue("loginPage", "titleTextFontSize");
        String actualFontSize = loginPage.getTitleElement().getCssValue("font-size");

        assertThat(actualFontSize, equalTo(expectedFontSize));

        String expectedTextColor = PropertyReader.getValue("loginPage","titleTextColor");
        String actualTextColor = loginPage.getLoginBtn().getCssValue("color");

        assertThat(actualTextColor, equalTo(expectedTextColor));

        String expectedFontFamily1 = PropertyReader.getValue("loginPage", "titleTextFontFamily1");
        String expectedFontFamily2 = PropertyReader.getValue("loginPage", "titleTextFontFamily2");
        String actualFontFamily = loginPage.getTitleElement().getCssValue("font-family");

        assertThat(actualFontFamily,
                allOf(
                        containsString(expectedFontFamily1),
                        containsString(expectedFontFamily2))
        );
        return this;
    }

    public LoginAction verifyBackgroundColor() {
        String expectedBackgroundColor = PropertyReader.getValue("loginPage", "backgroundColor");
        String actualBackgroundColor = loginPage.getLoginPageContainer().getCssValue("background-color");

        assertThat(actualBackgroundColor, equalTo(expectedBackgroundColor));
        return this;
    }

    public LoginAction verifyLoginPanel() {
        String expectedColor = PropertyReader.getValue("loginPage", "loginPanelColor");
        String actualColor = loginPage.getLoginPanel().getCssValue("background-color");

        assertThat(actualColor, equalTo(expectedColor));

        String expectedUsernamePlaceholder = PropertyReader.getValue("loginPage", "usernamePlaceholder");
        String expectedPasswordPlaceholder = PropertyReader.getValue("loginPage", "passwordPlaceholder");

        String actualUsernamePlaceholder = loginPage.getUserNameInput().getAttribute("placeholder");
        String actualPasswordPlaceholder = loginPage.getPasswordInput().getAttribute("placeholder");

        assertThat(actualUsernamePlaceholder, equalTo(expectedUsernamePlaceholder));
        assertThat(actualPasswordPlaceholder, equalTo(expectedPasswordPlaceholder));

        return this;
    }

    public void verifyLoginButton() {
        String expectedColor = PropertyReader.getValue("loginPage", "loginBtnColor");
        String actualColor = loginPage.getLoginBtn().getCssValue("background-color");

        assertThat(actualColor, equalTo(expectedColor));

        String expectedBorderRadius = PropertyReader.getValue("loginPage", "loginBtnBorderRadius");
        String actualBorderRadius = loginPage.getLoginBtn().getCssValue("border-radius");

        assertThat(actualBorderRadius, equalTo(expectedBorderRadius));

        String expectedBtnText = PropertyReader.getValue("loginPage", "loginBtnText");
        String actualBtnText = loginPage.getLoginBtn().getAttribute("value");

        assertThat(actualBtnText, equalTo(expectedBtnText));

        String expectedBtnTextColor = PropertyReader.getValue("loginPage", "loginBtnTextColor");
        String actualBtnTextColor = loginPage.getLoginBtn().getCssValue("color");

        assertThat(actualBtnTextColor, equalTo(expectedBtnTextColor));
    }

}

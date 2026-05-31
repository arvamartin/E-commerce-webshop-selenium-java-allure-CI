package pages;

import framework.core.Element;
import framework.core.BasePage;
import framework.utils.LoginPanelElementExpected;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement userNameInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(id = "login-button")
    private WebElement loginBtn;
    @FindBy(css = "[data-test='error']")
    private WebElement errorPopup;
    @FindBy(className = "login_logo")
    private WebElement titleElement;
    @FindBy(className = "login_container")
    private WebElement loginPageContainer;
    @FindBy(className = "login_wrapper-inner")
    private WebElement loginPanel;

    public void enterUsername(String username) {
        new Element(userNameInput)
                .waitForVisible()
                .clearAndType(username);
    }

    public void enterPassword(String password) {
        new Element(passwordInput)
                .waitForVisible()
                .clearAndType(password);
    }

    public void clickLoginButton() {
        new Element(loginBtn)
                .waitForClickable()
                .click();
    }

    public String getErrorPopupText() {
        return new Element(errorPopup)
                .waitForVisible()
                .getText();
    }

    public boolean isErrorPopupDisplayed() {
        return errorPopup.isDisplayed();
    }

    public String getLoginPageContainerCssValue(String cssProperty) {
        return element(loginPageContainer).getCssValue(cssProperty);
    }

    public Element resolveElement(LoginPanelElementExpected expected) {
        return switch (expected) {
            case TITLE -> element(titleElement);
            case LOGIN_PANEL -> element(loginPanel);
            case USERNAME_INPUT -> element(userNameInput);
            case PASSWORD_INPUT -> element(passwordInput);
            case LOGIN_BUTTON -> element(loginBtn);
        };
    }

    private Element element(WebElement webElement) {
        return new Element(webElement);
    }
}

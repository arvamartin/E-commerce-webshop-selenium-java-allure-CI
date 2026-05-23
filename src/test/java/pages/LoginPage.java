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
        return loginPageContainer.getCssValue(cssProperty);
    }

    public WebElement resolveElement(LoginPanelElementExpected expected) {
        return switch (expected) {
            case TITLE -> titleElement;
            case LOGIN_PANEL -> loginPanel;
            case USERNAME_INPUT -> userNameInput;
            case PASSWORD_INPUT -> passwordInput;
            case LOGIN_BUTTON -> loginBtn;
        };
    }

    public void waitForVisible(WebElement element) {
        new Element(element).waitForVisible();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    public String getCssValue(WebElement element, String cssProperty) {
        return element.getCssValue(cssProperty);
    }
}

package pages;

import framework.core.BasePage;
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


    public WebElement getUserNameInput() {
        return userNameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }

    public WebElement getErrorPopup() {
        return errorPopup;
    }

    public WebElement getTitleElement() {
        return titleElement;
    }

    public WebElement getLoginPageContainer() {
        return loginPageContainer;
    }

    public WebElement getLoginPanel() {
        return loginPanel;
    }
}

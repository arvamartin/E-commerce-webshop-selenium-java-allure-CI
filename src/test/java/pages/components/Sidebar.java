package pages.components;

import framework.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Sidebar extends BasePage {

    @FindBy(className = "bm-menu")
    private WebElement sidebarPanel;
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuBtn;
    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsBtn;
    @FindBy(id = "about_sidebar_link")
    private WebElement aboutBtn;
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutBtn;
    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeBtn;


    public WebElement getSidebarPanel() {
        return sidebarPanel;
    }

    public WebElement getMenuBtn() {
        return menuBtn;
    }

    public WebElement getAllItemsBtn() {
        return allItemsBtn;
    }

    public WebElement getAboutBtn() {
        return aboutBtn;
    }

    public WebElement getLogoutBtn() {
        return wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
    }

    public WebElement getCloseBtn() {
        return closeBtn;
    }
}

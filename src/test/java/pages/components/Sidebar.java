package pages.components;

import framework.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Sidebar extends BasePage {

    @FindBy(className = "bm-menu")
    private WebElement sidebarPanel;
    @FindBy(className = "bm-item-list")
    private List<WebElement> sidebarElements;
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

    public List<WebElement> getSidebarElements() {
        return sidebarElements;
    }

    public WebElement getSidebarPanel() {
        return sidebarPanel;
    }

    public WebElement getMenuBtn() {
        return wait.until(ExpectedConditions.elementToBeClickable(menuBtn));
    }

    public WebElement getAllItemsBtn() {
        return wait.until(ExpectedConditions.elementToBeClickable(allItemsBtn));
    }

    public WebElement getAboutBtn() {
        return wait.until(ExpectedConditions.elementToBeClickable(aboutBtn));
    }

    public WebElement getLogoutBtn() {
        return wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
    }

    public WebElement getCloseBtn() {
        wait.until(ExpectedConditions.visibilityOf(closeBtn));
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn));
        return closeBtn;
    }
}

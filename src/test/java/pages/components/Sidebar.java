package pages.components;

import framework.core.BasePage;
import framework.core.Browser;
import framework.core.Element;
import framework.utils.SidebarElementExpected;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class Sidebar extends BasePage {

    @FindBy(className = "bm-menu")
    private WebElement sidebarPanel;
    @FindBy(css = ".bm-item-list .bm-item")
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
        return new ArrayList<>(sidebarElements);
    }

    public void clickMenuButton() {
        new Element(menuBtn)
                .waitForClickable()
                .click();
    }

    public void waitForPanelVisible() {
        new Element(sidebarPanel)
                .waitForVisible();
    }

    public boolean isPanelDisplayed() {
        try {
            return sidebarPanel.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void waitForPanelInvisible() {
        new Element(sidebarPanel)
                .waitForInvisible();
    }

    public void clickLogoutButton() {
        new Element(logoutBtn).waitForClickable().click();
    }

    public void clickAboutButton() {
        new Element(aboutBtn).waitForClickable().click();
    }

    public void clickAllItemsButton() {
        new Element(allItemsBtn).waitForClickable().click();
    }

    public void clickCloseButton() {
        new Element(closeBtn)
                .waitForClickable()
                .javascriptExecutorClick(Browser.getDriver());
    }

    public void waitForVisible(WebElement element) {
        new Element(element).waitForVisible();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getCssValue(WebElement element, String cssProperty) {
        return element.getCssValue(cssProperty);
    }

    public String getPanelCssValue(String cssProperty) {
        return element(sidebarPanel).getCssValue(cssProperty);
    }

    public Element resolveElement(SidebarElementExpected expected) {
        return switch (expected) {
            case ALL_ITEMS -> element(allItemsBtn);
            case ABOUT -> element(aboutBtn);
            case LOGOUT -> element(logoutBtn);
        };
    }

    private Element element(WebElement webElement) {
        return new Element(webElement);
    }
}

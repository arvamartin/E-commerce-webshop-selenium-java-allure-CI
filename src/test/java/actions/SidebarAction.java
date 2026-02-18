package actions;

import framework.core.Browser;
import framework.core.Element;
import framework.core.PropertyReader;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.components.Sidebar;


public class SidebarAction extends BaseAction<SidebarAction> {

    private Sidebar sidebar;
    private final WebDriver driver;


    public SidebarAction() {
        this.sidebar = new Sidebar();
        driver = Browser.getDriver();
    }

    public SidebarAction navigateTo(String url) {
        sidebar.navigateTo(url);
        return this;
    }

    public SidebarAction openSidebar() {
        new Element(sidebar.getMenuBtn())
                .waitForClickable().click();

        new Element(sidebar.getSidebarPanel())
                .shouldBeVisible();

        return this;
    }


    public SidebarAction validatePresenceOfSidebarItems() {
        for (WebElement element : sidebar.getSidebarElements()) {
            try {
                new Element(element).waitForVisible();
            } catch (TimeoutException e) {
                throw new AssertionError(
                        "Sidebar element not visible: " + element.getText(), e
                );
            }
        }
        return this;
    }

    public void sidebarMenuIsNotDisplayed() {
        WebElement sidebarPanel = sidebar.getSidebarPanel();

        new Element(sidebarPanel)
                .waitForInvisible()
                .shouldNotBeVisible();
    }


    public SidebarAction logout() {
        new Element(sidebar.getLogoutBtn()).waitForClickable().click();
        return this;
    }

    public SidebarAction clickOnAboutBtn() {
        new Element(sidebar.getAboutBtn()).waitForClickable().click();
        return this;
    }

    public SidebarAction clickOnAllItemsBtn() {
        new Element(sidebar.getAllItemsBtn()).waitForClickable().click();
        return this;
    }

    public SidebarAction clickOnCloseCross() {
        new Element(sidebar.getCloseBtn()).waitForClickable().javascriptExecutorClick(driver);
        return this;
    }

    public SidebarAction verifyPanelBackgroundColor() {
        new Element(sidebar.getSidebarPanel()).assertCssValue("background-color", sidebarProp("panelBackgroundColor"));
        return this;
    }

    public SidebarAction verifyPanelElements() {
        new Element(sidebar.getAllItemsBtn()).assertText(sidebarProp("allItemText"));
        new Element(sidebar.getAboutBtn()).assertText(sidebarProp("aboutText"));
        new Element(sidebar.getLogoutBtn()).assertText(sidebarProp("logoutText"));


        String expectedFontFamily1 = sidebarProp("panelElementsFontFamily1");
        String expectedFontFamily2 = sidebarProp("panelElementsFontFamily2");
        String expectedFontFamily3 = sidebarProp("panelElementsFontFamily3");
        String expectedFontFamily4 = sidebarProp("panelElementsFontFamily4");

        for (WebElement element : sidebar.getSidebarElements()) {
            try {
                new Element(element)
                        .assertCssValueContains("font-family", expectedFontFamily1, expectedFontFamily2, expectedFontFamily3, expectedFontFamily4)
                        .assertCssValue("color", sidebarProp("panelElementsTextColor"))
                        .assertCssValue("border-bottom-color",sidebarProp("elementBorderBottomColor"));
            } catch (TimeoutException e) {
                throw new AssertionError(
                        "Sidebar element not visible: " + element.getText(), e
                );
            }
        }
        return this;
    }

    private String sidebarProp(String key) {
        return PropertyReader.getValue("sidebarUi", "sidebar", key);
    }
}
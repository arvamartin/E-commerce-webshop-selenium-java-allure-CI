package actions;

import framework.core.Browser;
import framework.core.Element;
import framework.core.PropertyReader;
import framework.utils.SidebarElementExpected;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.components.Sidebar;

import java.util.List;


public class SidebarAction extends BaseAction<SidebarAction> {

    private Sidebar sidebar;
    private final WebDriver driver;


    public SidebarAction() {
        this.sidebar = new Sidebar();
        driver = Browser.getDriver();
    }

    @Step("Opens Sidebar")
    public SidebarAction openSidebar() {
        new Element(sidebar.getMenuBtn())
                .waitForClickable().click();

        new Element(sidebar.getSidebarPanel())
                .shouldBeVisible();

        return this;
    }

    @Step("Validates presence of items")
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

    @Step("Verifies Sidebar menu is not displayed")
    public void sidebarMenuIsNotDisplayed() {
        WebElement sidebarPanel = sidebar.getSidebarPanel();

        new Element(sidebarPanel)
                .waitForInvisible()
                .shouldNotBeVisible();
    }

    @Step("Clicks on Logout button")
    public SidebarAction logout() {
        new Element(sidebar.getLogoutBtn()).waitForClickable().click();
        return this;
    }

    @Step("Clicks on About button")
    public SidebarAction clickOnAboutBtn() {
        new Element(sidebar.getAboutBtn()).waitForClickable().click();
        return this;
    }

    @Step("Clicks on all All Items button")
    public SidebarAction clickOnAllItemsBtn() {
        new Element(sidebar.getAllItemsBtn()).waitForClickable().click();
        return this;
    }

    @Step("Clicks on Close cross")
    public SidebarAction clickOnCloseCross() {
        new Element(sidebar.getCloseBtn()).waitForClickable().javascriptExecutorClick(driver);
        return this;
    }

    @Step("Verifies background color")
    public SidebarAction verifyPanelBackgroundColor() {
        new Element(sidebar.getSidebarPanel()).assertCssValue("background-color", sidebarProp("panelBackgroundColor"));
        return this;
    }

    @Step("Verifies panel elements")
    public void verifyPanelElements() {

        for (SidebarElementExpected expected : SidebarElementExpected.values()) {

            WebElement webElement = resolveElement(expected);
            Element element = new Element(webElement).waitForVisible();

            element.assertText(sidebarProp(expected.getTextKey()));
            expected.getCss().forEach((cssKey, cssValue) -> {

                if (cssValue instanceof List<?>) {
                    List<String> values = ((List<String>) cssValue)
                            .stream()
                            .map(this::sidebarProp)
                            .toList();

                    element.assertCssValueContains(cssKey, values.toArray(new String[0]));

                } else {
                    element.assertCssValue(cssKey, sidebarProp((String) cssValue));
                }
            });
        }
    }

    public WebElement resolveElement(SidebarElementExpected expected) {
        return switch (expected) {
            case ALL_ITEMS -> sidebar.getAllItemsBtn();
            case ABOUT -> sidebar.getAboutBtn();
            case LOGOUT -> sidebar.getLogoutBtn();
        };
    }

    private String sidebarProp(String key) {
        return PropertyReader.getValue("sidebarUi", "sidebar", key);
    }
}
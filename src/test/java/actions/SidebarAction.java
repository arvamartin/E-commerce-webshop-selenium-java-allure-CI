package actions;

import framework.core.Browser;
import framework.core.Element;
import framework.core.PropertyReader;
import framework.utils.SidebarElementExpected;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import pages.components.Sidebar;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class SidebarAction extends BaseAction<SidebarAction> {

    private final Sidebar sidebar;


    public SidebarAction() {
        sidebar = new Sidebar();
    }

    @Step("Opens Sidebar")
    public SidebarAction openSidebar() {
        new Element(sidebar.getMenuBtn())
                .waitForClickable().click();

        new Element(sidebar.getSidebarPanel())
                .waitForVisible()
                .shouldBeVisible();

        return this;
    }

    @Step("Validates presence of items")
    public SidebarAction validatePresenceOfSidebarItems() {
        List<WebElement> sidebarElements = sidebar.getSidebarElements();
        assertThat("Sidebar items list is unexpectedly empty", sidebarElements.size(), greaterThanOrEqualTo(3));

        for (WebElement element : sidebarElements) {
            try {
                new Element(element).waitForVisible();
            } catch (TimeoutException e) {
                String elementHint = element.getAttribute("id");
                if (elementHint == null || elementHint.isBlank()) {
                    elementHint = element.getAttribute("class");
                }
                throw new AssertionError(
                        "Sidebar element not visible: " + elementHint, e
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
        new Element(sidebar.getCloseBtn())
                .waitForClickable()
                .javascriptExecutorClick(Browser.getDriver());
        return this;
    }

    @Step("Verifies background color")
    public SidebarAction verifyPanelBackgroundColor() {
        new Element(sidebar.getSidebarPanel())
                .waitForVisible()
                .assertCssValue("background-color", sidebarProp("panelBackgroundColor"));
        return this;
    }

    @Step("Verifies panel elements")
    public SidebarAction verifyPanelElements() {

        for (SidebarElementExpected expected : SidebarElementExpected.values()) {

            WebElement webElement = resolveElement(expected);
            Element element = new Element(webElement).waitForVisible();

            element.assertText(sidebarProp(expected.getTextKey()));
            expected.getCss().forEach((cssKey, cssValue) -> {

                if (cssValue instanceof List<?>) {
                    List<String> values = ((List<?>) cssValue)
                            .stream()
                            .map(String.class::cast)
                            .map(this::sidebarProp)
                            .toList();

                    element.assertCssValueContains(cssKey, values.toArray(new String[0]));

                } else {
                    element.assertCssValue(cssKey, sidebarProp((String) cssValue));
                }
            });
        }
        return this;
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

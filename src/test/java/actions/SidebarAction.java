package actions;

import framework.core.PropertyReader;
import framework.utils.CssExpectation;
import framework.utils.CssMatchType;
import framework.utils.SidebarElementExpected;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import pages.components.Sidebar;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class SidebarAction extends BaseAction<SidebarAction> {

    private final Sidebar sidebar;


    public SidebarAction() {
        sidebar = new Sidebar();
    }

    @Step("Opens Sidebar")
    public SidebarAction openSidebar() {
        sidebar.clickMenuButton();
        sidebar.waitForPanelVisible();
        assertThat(sidebar.isPanelDisplayed(), is(true));

        return this;
    }

    @Step("Validates presence of items")
    public SidebarAction validatePresenceOfSidebarItems() {
        List<WebElement> sidebarElements = sidebar.getSidebarElements();
        assertThat("Sidebar items list is unexpectedly empty", sidebarElements.size(), greaterThanOrEqualTo(3));

        for (WebElement element : sidebarElements) {
            try {
                sidebar.waitForVisible(element);
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
        sidebar.waitForPanelInvisible();
        assertThat(sidebar.isPanelDisplayed(), is(false));
    }

    @Step("Clicks on Logout button")
    public SidebarAction logout() {
        sidebar.clickLogoutButton();
        return this;
    }

    @Step("Clicks on About button")
    public SidebarAction clickOnAboutBtn() {
        sidebar.clickAboutButton();
        return this;
    }

    @Step("Clicks on all All Items button")
    public SidebarAction clickOnAllItemsBtn() {
        sidebar.clickAllItemsButton();
        return this;
    }

    @Step("Clicks on Close cross")
    public SidebarAction clickOnCloseCross() {
        sidebar.clickCloseButton();
        return this;
    }

    @Step("Verifies background color")
    public SidebarAction verifyPanelBackgroundColor() {
        sidebar.waitForPanelVisible();
        assertThat(sidebar.getPanelCssValue("background-color"), equalTo(sidebarProp("panelBackgroundColor")));
        return this;
    }

    @Step("Verifies panel elements")
    public SidebarAction verifyPanelElements() {

        for (SidebarElementExpected expected : SidebarElementExpected.values()) {

            WebElement webElement = resolveElement(expected);
            sidebar.waitForVisible(webElement);

            assertThat(sidebar.getText(webElement), equalTo(sidebarProp(expected.getTextKey())));
            for (CssExpectation cssExpectation : expected.getCssExpectations()) {
                List<String> values = cssExpectation.expectedValueKeys()
                        .stream()
                        .map(this::sidebarProp)
                        .toList();

                String actualValue = sidebar.getCssValue(webElement, cssExpectation.cssProperty());
                if (cssExpectation.matchType() == CssMatchType.CONTAINS_ALL) {
                    for (String value : values) {
                        assertThat(actualValue, containsString(value));
                    }
                } else if (cssExpectation.matchType() == CssMatchType.EXACT) {
                    assertThat(actualValue, equalTo(values.get(0)));
                }
            }
        }
        return this;
    }

    public WebElement resolveElement(SidebarElementExpected expected) {
        return sidebar.resolveElement(expected);
    }

    private String sidebarProp(String key) {
        return PropertyReader.getValue("sidebarUi", "sidebar", key);
    }
}

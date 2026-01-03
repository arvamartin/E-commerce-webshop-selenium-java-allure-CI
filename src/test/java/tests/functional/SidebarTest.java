package tests.functional;

import actions.LoginAction;
import actions.SidebarAction;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static framework.core.Constants.*;

@Epic("Navigation")
@Feature("Sidebar")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("functional")

public class SidebarTest {
    private SidebarAction sidebarAction;

    @BeforeEach
    public void setUp() {
        sidebarAction = new SidebarAction();
        LoginAction loginAction = new LoginAction();

        loginAction.loginAsStandardUser();
    }

    @AfterEach
    public void tearDown() {
        Browser.quitDriver();
    }

    @Test
    @Story("Sidebar navigation - display and close")
    @DisplayName("Sidebar menu displays items and can be closed")
    @Description("Verify that the sidebar menu displays all items and can be closed successfully")
    public void successfulDisplaysSidebarItemsAndCloseThePanel() {
        sidebarAction
                .openSidebar()
                .validatePresenceOfSidebarItems()
                .clickOnCloseCross()
                .sidebarMenuIsNotDisplayed();
    }

    @Test
    @Story("Sidebar navigation - logout")
    @DisplayName("Successful logout from sidebar")
    @Description("Verify that a logged in user is able to log out using the sidebar menu")
    public void successfulLogoutFromSidebar() {
        sidebarAction
                .openSidebar()
                .logout()
                .validateCurrentPage(LOGIN_URL.getValue());
    }

    @Test
    @Story("Sidebar navigation - about")
    @DisplayName("Successful navigated to about site")
    @Description("Verify that a logged in user is able to reach about site using the sidebar menu")
    public void successfulAboutSiteFromSidebar(){
        sidebarAction
                .openSidebar()
                .clickOnAboutBtn()
                .validateCurrentPage(ABOUT_PAGE_URL.getValue());
    }

    @Test
    @Story("Sidebar navigation - all items")
    @DisplayName("Successful navigated to inventory page")
    @Description("Verify that a logged in user is able to reach inventory page using the sidebar menu")
    public void successfulAllItemsFromSidebar(){
        sidebarAction
                .openSidebar()
                .navigateTo("https://www.saucedemo.com/inventory-item.html?id=1")
                .openSidebar()
                .clickOnAllItemsBtn()
                .validateCurrentPage(HOME_PAGE_URL.getValue());
    }
}

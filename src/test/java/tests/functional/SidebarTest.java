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
    private LoginAction loginAction;

    @BeforeEach
    public void setUp() {
        sidebarAction = new SidebarAction();
        loginAction = new LoginAction();

        loginAction.login();
    }

    @AfterEach
    public void tearDown() {
        Browser.quitDriver();
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

//    public void successfulAboutSiteFromSidebar(){
//        sidebarAction
//                .openSidebar()
//    }
}

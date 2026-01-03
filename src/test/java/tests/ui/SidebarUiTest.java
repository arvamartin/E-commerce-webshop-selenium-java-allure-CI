package tests.ui;

import actions.LoginAction;
import actions.SidebarAction;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

@Epic("UI Verification")
@Feature("Sidebar")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("UI")
public class SidebarUiTest {
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
    @Story("Sidebar UI specification")
    @DisplayName("UI matches design specification")
    @Description("Verify that the sidebar visual properties match the approved UI specification:\n" +
            "- background color\n" +
            "- font size and colors\n")
    public void uiVerification() {
        sidebarAction.openSidebar()
                .verifyPanelBackgroundColor()
                .verifyPanelElements();
    }
}

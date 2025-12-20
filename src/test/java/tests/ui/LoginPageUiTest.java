package tests.ui;

import actions.LoginAction;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static framework.core.Constants.LOGIN_URL;

@Epic("UI Verification")
@Feature("Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("UI")


public class LoginPageUiTest {

    private LoginAction loginAction;

    @BeforeEach
    public void setUp() {
        loginAction = new LoginAction();
    }

    @AfterEach
    public void tearDown() {
        Browser.quitDriver();
    }


    @Test
    @Story("Login UI specification")
    @DisplayName("UI matches design specification")
    @Description("Verify that the login page visual properties match the approved UI specification:\n" +
            "- background color\n" +
            "- font size and colors\n")
    public void uiVerification() {
        loginAction.navigateTo(LOGIN_URL.getValue())
                .verifyTitleText()
                .verifyBackgroundColor()
                .verifyLoginPanel()
                .verifyLoginButton();
    }
}

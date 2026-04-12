package tests.ui;

import actions.LoginAction;
import allure.AllureScreenshotListener;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import tests.BaseTest;

import static framework.core.Constants.LOGIN_URL;

@Epic("UI Verification")
@Feature("Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("UI")
public class LoginPageUiTest extends BaseTest {

    private LoginAction loginAction;

    @BeforeEach
    public void setUp() {
        loginAction = new LoginAction();
        loginAction.navigateTo(LOGIN_URL.getValue());
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
        loginAction
                .verifyBackgroundColor()
                .verifyLoginPanel();
    }
}

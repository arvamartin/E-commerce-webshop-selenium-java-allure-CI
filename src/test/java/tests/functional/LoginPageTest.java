package tests.functional;

import actions.LoginAction;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static framework.core.Constants.*;

@Epic("Authentication")
@Feature("Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("functional")


public class LoginPageTest {

    private LoginAction loginAction;

    @BeforeEach
    public void setUp(){
        loginAction = new LoginAction();
    }

    @AfterEach
    public void tearDown(){
        Browser.quitDriver();
    }


    @Test
    @Story("Successful authentication")
    @DisplayName("Successful login with valid credentials")
    @Description("Verify that a standard user is able to log in using valid credentials")
    public void successfulLoginWithValidCredentials(){
        loginAction
                .navigateTo(LOGIN_URL.getValue())
                .enterUsername(STANDARD_USER.getValue())
                .enterPassword(STANDARD_PASSWORD.getValue())
                .clickOnLoginBtn()
                .validateCurrentPage(HOME_PAGE_URL.getValue());
    }


    @ParameterizedTest(name = "{index} => username = {0}, password= {1}, url = {2}, error = {3}")
    @CsvFileSource(resources = "/loginTestData.csv", numLinesToSkip = 1)
    @Story("Unsuccessful authentication")
    @DisplayName("Unsuccessful login with invalid credentials")
    @Description("Verify that user is unable to log in using invalid credentials.")
    public void unSuccessfulLogin(String username, String password, String expectedUrl, String expectedErrorMessage){
        loginAction
                .navigateTo(LOGIN_URL.getValue())
                .enterUsername(username)
                .enterPassword(password)
                .clickOnLoginBtn()
                .isErrorPopupDisplayedWithMessage(expectedErrorMessage)
                .validateCurrentPage(expectedUrl);
    }


    @Test
    @Story("Access control for protected resources")
    @DisplayName("Unsuccessful login without login process")
    @Description("Verify that inventory page cannot be accessed without login process")
    public void unSuccessfulDirectInventoryAccess(){
        loginAction
                .navigateTo(HOME_PAGE_URL.getValue())
                .isErrorPopupDisplayedWithMessage("Epic sadface: You can only access '/inventory.html' when you are logged in.")
                .validateCurrentPage(LOGIN_URL.getValue());
    }

}

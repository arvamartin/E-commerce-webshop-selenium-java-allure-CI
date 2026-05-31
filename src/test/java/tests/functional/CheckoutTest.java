package tests.functional;

import actions.CheckoutAction;
import actions.LoginAction;
import actions.ProductAction;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static framework.core.Constants.CART_PAGE_URL;
import static framework.core.Constants.CHECKOUT_COMPLETE_URL;
import static framework.core.Constants.CHECKOUT_STEP_ONE_URL;
import static framework.core.Constants.CHECKOUT_STEP_TWO_URL;

@Epic("Shopping")
@Feature("Checkout")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("functional")
public class CheckoutTest extends BaseTest {
    private ProductAction productAction;
    private CheckoutAction checkoutAction;

    @BeforeEach
    public void setUp() {
        LoginAction loginAction = new LoginAction();
        productAction = new ProductAction();
        checkoutAction = new CheckoutAction();

        loginAction.loginAsStandardUser();
    }

    @Test
    @Story("Order lifecycle")
    @DisplayName("User can complete checkout with multiple products")
    @Description("Verify the full order lifecycle from cart creation through checkout overview and order confirmation")
    public void userCanCompleteCheckoutWithMultipleProducts() {
        productAction
                .addProductsToCart("Sauce Labs Backpack", "Sauce Labs Bike Light")
                .navigateTo(CART_PAGE_URL.getValue());

        checkoutAction
                .startCheckout()
                .validateCurrentPage(CHECKOUT_STEP_ONE_URL.getValue());

        checkoutAction
                .fillCustomerInformation("Alex", "Tester", "1011")
                .continueToOverview()
                .validateCurrentPage(CHECKOUT_STEP_TWO_URL.getValue());

        checkoutAction
                .validateOverviewContainsProducts("Sauce Labs Backpack", "Sauce Labs Bike Light")
                .finishOrder()
                .validateCurrentPage(CHECKOUT_COMPLETE_URL.getValue());

        checkoutAction.validateOrderComplete();
    }
}

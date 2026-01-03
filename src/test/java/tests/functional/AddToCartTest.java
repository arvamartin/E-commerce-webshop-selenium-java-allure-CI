package tests.functional;

import actions.LoginAction;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

@Epic("Shopping")
@Feature("Add to Cart")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("functional")

public class AddToCartTest {

    @BeforeEach
    public void setUp() {

        LoginAction loginAction = new LoginAction();

        loginAction.loginAsStandardUser();
    }

    @AfterEach
    public void tearDown() {
        Browser.quitDriver();
    }

    @Test
    @Story("Add single product to cart")
    @DisplayName("Add single product to cart from products page")
    @Description("Verify that a logged in user can add a single product to the cart and the cart badge is updated accordingly")
    public void addSingleProductToCart() {


    }


    @Test
    @Story("Add multiple products to cart")
    @DisplayName("Add multiple products to cart")
    @Description("Verify that user can add multiple different products to the cart and cart badge shows correct count")
    public void addMultipleProductsToCart() {


    }

    @Test
    @Story("Add product to cart from product details page")
    @DisplayName("Add product to cart from product details page")
    @Description("Verify that user can add a product to the cart from the inventory item details page")
    public void addProductToCartFromProductDetailsPage() {


    }


    @Test
    @Story("Remove product from cart")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Remove product from cart from products page")
    @Description("Verify that user is able to remove a product from the cart and cart badge is updated")
    public void removeProductFromCart() {


    }

    @Test
    @Story("Cart state persistence")
    @DisplayName("Cart state should persist after navigation")
    @Description("Verify that products added to the cart remain in the cart after navigating between pages")
    public void cartStateShouldPersistAfterNavigation() {


    }

    @Test
    @Story("Prevent duplicate product addition")
    @DisplayName("Same product cannot be added twice to the cart")
    @Description("Verify that clicking Add to Cart multiple times for the same product does not increase cart count")
    public void sameProductCannotBeAddedTwice() {


    }

}

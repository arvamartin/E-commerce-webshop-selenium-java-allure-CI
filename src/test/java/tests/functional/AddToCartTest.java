package tests.functional;

import actions.CartAction;
import actions.LoginAction;
import actions.ProductAction;
import framework.core.Browser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import tests.BaseTest;

@Epic("Shopping")
@Feature("Add to Cart")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA")
@Tag("functional")

public class AddToCartTest extends BaseTest {
    private LoginAction loginAction;
    private ProductAction productAction;
    private CartAction cartAction;

    @BeforeEach
    public void setUp() {
        loginAction = new LoginAction();
        productAction = new ProductAction();
        cartAction = new CartAction();
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
        productAction.
                addProductsToCart("Sauce Labs Backpack")
                .validateRemoveButtonIsDisplayed()
                .validateCartBadgeCount(1);
    }


    @Test
    @Story("Add multiple products to cart")
    @DisplayName("Add multiple products to cart")
    @Description("Verify that user can add multiple different products to the cart and cart badge shows correct count")
    public void addMultipleProductsToCart() {
        productAction
                .addProductsToCart("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Onesie")
                .validateCartBadgeCount(3);
    }

    @Test
    @Story("Add product to cart from product details page")
    @DisplayName("Add product to cart from product details page")
    @Description("Verify that user can add a product to the cart from the inventory item details page")
    public void addProductToCartFromProductDetailsPage() {
        productAction
                .navigateTo("https://www.saucedemo.com/inventory-item.html?id=4")
                .addProductToCart()
                .validateRemoveButtonIsDisplayed()
                .navigateTo("https://www.saucedemo.com/inventory-item.html?id=2")
                .addProductToCart()
                .validateRemoveButtonIsDisplayed()
                .validateCartBadgeCount(2);
    }


    @Test
    @Story("Remove product from cart")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Remove product from cart from products page")
    @Description("Verify that user is able to remove a product from the cart and cart badge is updated")
    public void removeProductFromCart() {
        productAction.
                addProductsToCart("Sauce Labs Backpack")
                .removeProductsFromCart()
                .validateAddToCartButtonIsDisplayed()
                .validateCartBadgeIsNotDisplayed();
    }

    @Test
    @Story("Cart state persistence")
    @DisplayName("Cart state should persist after navigation")
    @Description("Verify that products added to the cart remain in the cart after navigating between pages")
    public void cartStateShouldPersistAfterNavigation() {
        productAction
                .addProductsToCart("Sauce Labs Backpack")
                .navigateTo("https://www.saucedemo.com/cart.html")
                .navigateTo("https://www.saucedemo.com/inventory.html")
                .validateRemoveButtonIsDisplayed()
                .validateCartBadgeCount(1);
    }

    @Test
    @Story("View cart contents")
    @DisplayName("Added products are displayed on cart page")
    @Description("Verify that products added to the cart are correctly displayed on the cart page")
    public void addedProductsAreDisplayedOnCartPage() {

        productAction
                .addProductsToCart("Sauce Labs Backpack", "Sauce Labs Bike Light")
                .navigateTo("https://www.saucedemo.com/cart.html");
        cartAction
                .validateProductsAndPricesInCart("Sauce Labs Backpack", "$29.99")
                .validateProductsAndPricesInCart("Sauce Labs Bike Light", "$9.99");

    }
}

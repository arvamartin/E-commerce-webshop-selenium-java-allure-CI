package actions;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.CartPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class CartAction extends BaseAction<CartAction> {
    private CartPage cartPage;

    public CartAction() {
        this.cartPage = new CartPage();
    }


    @Step("Validates products and prices are displayed in cart")
    public CartAction validateProductsAndPricesInCart(String productName, String expectedPrice) {

        List<String> names = cartPage.getCartItemNames()
                .stream()
                .map(WebElement::getText)
                .toList();

        List<String> prices = cartPage.getCartItemPrices()
                .stream()
                .map(WebElement::getText)
                .toList();

        int index = names.indexOf(productName);

        assertThat("Product not found in cart: " + productName, index, greaterThanOrEqualTo(0));
        assertThat("Invalid price for product: " + productName, prices.get(index), equalTo(expectedPrice));
        return this;
    }
}


package actions;

import framework.core.Element;
import io.qameta.allure.Step;
import pages.InventoryPage;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProductAction extends BaseAction<ProductAction>{
    private final InventoryPage inventoryPage;
    private final Set<String> trackedProducts = new LinkedHashSet<>();
    private boolean productAddedFromDetailsPage = false;

    public ProductAction() {
        this.inventoryPage = new InventoryPage();
    }

    @Step("User adds product(s) to the cart")
    public ProductAction addProductsToCart(String... productNames) {
        if (productNames == null || productNames.length == 0) {
            throw new IllegalArgumentException("At least one product name is required.");
        }

        trackedProducts.clear();
        productAddedFromDetailsPage = false;

        for (String productName : productNames) {
            trackedProducts.add(productName);
            new Element(inventoryPage.getAddToCartButton(productName))
                    .waitForClickable()
                    .click();
        }
        return this;
    }

    @Step("User adds product to the cart")
    public ProductAction addProductToCart(){
        productAddedFromDetailsPage = true;
        new Element(inventoryPage.getAddToCartButton())
                .waitForClickable()
                .click();
        return this;
    }

    @Step("Validates remove button is displayed for added products")
    public ProductAction validateRemoveButtonIsDisplayed() {
        if (!trackedProducts.isEmpty()) {
            for (String product : trackedProducts) {
                new Element(inventoryPage.getRemoveBtnForProduct(product))
                        .waitForVisible()
                        .assertText("Remove");
            }
            return this;
        }

        if (productAddedFromDetailsPage) {
            new Element(inventoryPage.getRemoveBtn())
                    .waitForVisible()
                    .assertText("Remove");
            return this;
        }

        throw new IllegalStateException("No tracked products to validate. Add product(s) to cart before validation.");
    }

    @Step("Validates badge count")
    public void validateCartBadgeCount(int count){
        new Element(inventoryPage.getShoppingCartBadge()).waitForVisible()
                .assertText(String.valueOf(count));
    }

    @Step("Removes products from cart")
    public ProductAction removeProductsFromCart(){
        ensureTrackedProducts("remove products from cart");
        for (String product : trackedProducts) {
            new Element(inventoryPage.getRemoveBtnForProduct(product))
                    .waitForClickable()
                    .click();
        }
        return this;
    }

    @Step("Validates add to cart button is displayed")
    public ProductAction validateAddToCartButtonIsDisplayed(){
        ensureTrackedProducts("validate add to cart buttons");
        for (String product : trackedProducts) {
            new Element(inventoryPage.getAddToCartButton(product))
                    .waitForVisible()
                    .assertText("Add to cart");
        }
        return this;
    }

    public void validateCartBadgeIsNotDisplayed(){
        assertThat(inventoryPage.isShoppingCartBadgeVisible(), is(false));
    }

    private void ensureTrackedProducts(String operation) {
        if (trackedProducts.isEmpty()) {
            throw new IllegalStateException("Cannot " + operation + " because no products were tracked.");
        }
    }
}

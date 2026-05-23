package actions;

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
            String normalizedProductName = requireProductName(productName);
            trackedProducts.add(normalizedProductName);
            inventoryPage.clickAddToCartButton(normalizedProductName);
        }
        return this;
    }

    @Step("User adds product to the cart")
    public ProductAction addProductToCart(){
        productAddedFromDetailsPage = true;
        inventoryPage.clickAddToCartButton();
        return this;
    }

    @Step("Validates remove button is displayed for added products")
    public ProductAction validateRemoveButtonIsDisplayed() {
        if (!trackedProducts.isEmpty()) {
            for (String product : trackedProducts) {
                assertThat("Unexpected element text", inventoryPage.getRemoveButtonTextForProduct(product), is("Remove"));
            }
            return this;
        }

        if (productAddedFromDetailsPage) {
            assertThat("Unexpected element text", inventoryPage.getRemoveButtonText(), is("Remove"));
            return this;
        }

        throw new IllegalStateException("No tracked products to validate. Add product(s) to cart before validation.");
    }

    @Step("Validates badge count")
    public void validateCartBadgeCount(int count){
        assertThat("Unexpected element text", inventoryPage.getShoppingCartBadgeText(), is(String.valueOf(count)));
    }

    @Step("Removes products from cart")
    public ProductAction removeProductsFromCart(){
        ensureTrackedProducts("remove products from cart");
        for (String product : trackedProducts) {
            inventoryPage.clickRemoveButtonForProduct(product);
        }
        return this;
    }

    @Step("Validates add to cart button is displayed")
    public ProductAction validateAddToCartButtonIsDisplayed(){
        ensureTrackedProducts("validate add to cart buttons");
        for (String product : trackedProducts) {
            assertThat("Unexpected element text", inventoryPage.getAddToCartButtonText(product), is("Add to cart"));
        }
        return this;
    }

    public void validateCartBadgeIsNotDisplayed(){
        inventoryPage.waitForShoppingCartBadgeToDisappear();
        assertThat(inventoryPage.isShoppingCartBadgeVisible(), is(false));
    }

    private void ensureTrackedProducts(String operation) {
        if (trackedProducts.isEmpty()) {
            throw new IllegalStateException("Cannot " + operation + " because no products were tracked.");
        }
    }

    private String requireProductName(String productName) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name must not be null or blank.");
        }
        return productName.trim();
    }
}

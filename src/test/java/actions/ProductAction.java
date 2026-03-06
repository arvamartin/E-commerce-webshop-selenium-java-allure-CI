package actions;

import framework.core.Element;
import io.qameta.allure.Step;
import pages.InventoryPage;

import java.util.ArrayList;
import java.util.List;

public class ProductAction extends BaseAction<ProductAction>{
    private InventoryPage inventoryPage;
    private List<String> addedProductsList;

    public ProductAction() {
        this.inventoryPage = new InventoryPage();
    }

    @Step("User adds product(s) to the cart")
    public ProductAction addProductsToCart(String... productNames) {
        this.addedProductsList = new ArrayList<>();

        for (String productName : productNames) {
            addedProductsList.add(productName);
            new Element(inventoryPage.getAddToCartButton(productName)).click();
        }
        return this;
    }

    @Step("User adds product to the cart")
    public ProductAction addProductToCart(){
            new Element(inventoryPage.getAddToCartButton()).click();
        return this;
    }

    @Step("Validates remove button is displayed for added products")
    public ProductAction validateRemoveButtonIsDisplayed() {
        if (addedProductsList != null) {
            for (String product : addedProductsList) {
                new Element(inventoryPage.getRemoveBtnForProduct(product)).assertText("Remove");
            }
        }else {
            new Element(inventoryPage.getRemoveBtn()).assertText("Remove");
        }
        return this;
    }

    @Step("Validates badge count")
    public void validateCartBadgeCount(int count){
        new Element(inventoryPage.getShoppingCartBadge()).waitForVisible()
                .assertText(String.valueOf(count));
    }

    @Step("Removes products from cart")
    public ProductAction removeProductsFromCart(){
        for (String product : addedProductsList) {
            new Element(inventoryPage.getRemoveBtnForProduct(product)).click();
        }
        return this;
    }

    @Step("Validates add to cart button is displayed")
    public ProductAction validateAddToCartButtonIsDisplayed(){
        for (String product : addedProductsList) {
            new Element(inventoryPage.getAddToCartButton(product))
                    .assertText("Add to cart");
        }
        return this;
    }

    public void validateCartBadgeIsNotDisplayed(){
        new Element(inventoryPage.getShoppingCartBadge()).shouldBeVisible();
    }

}

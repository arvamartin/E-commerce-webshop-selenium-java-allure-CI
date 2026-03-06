package pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage extends BasePage {

    @FindBy(className = "shopping_cart_link")
    WebElement shoppingCartBadge;

    public WebElement getAddToCartButton(String productName) {
        String formattedName = productName.toLowerCase().replace(" ", "-");
        return driver.findElement(By.id("add-to-cart-" + formattedName));
    }

    public WebElement getAddToCartButton() {
        return driver.findElement(By.id("add-to-cart"));
    }

    public WebElement getRemoveBtnForProduct(String productName) {
        String formattedName = formatProductName(productName);
        return driver.findElement(By.id("remove-" + formattedName));
    }

    public WebElement getRemoveBtn() {
        return driver.findElement(By.id("remove"));
    }

    private String formatProductName(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }

    public WebElement getShoppingCartBadge() {
        return shoppingCartBadge;
    }
}

package pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Locale;

public class InventoryPage extends BasePage {
    private static final By SHOPPING_CART_BADGE = By.className("shopping_cart_badge");

    public WebElement getAddToCartButton(String productName) {
        String formattedName = formatProductName(productName);
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
        return productName
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", "-");
    }

    public WebElement getShoppingCartBadge() {
        return driver.findElement(SHOPPING_CART_BADGE);
    }

    public boolean isShoppingCartBadgeVisible() {
        return !driver.findElements(SHOPPING_CART_BADGE).isEmpty();
    }
}

package pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Locale;

public class InventoryPage extends BasePage {
    @FindBy(id = "add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id = "remove")
    private WebElement removeButton;

    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartBadge;

    public WebElement getAddToCartButton(String productName) {
        String formattedName = formatProductName(productName);
        return driver.findElement(By.id("add-to-cart-" + formattedName));
    }

    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    public WebElement getRemoveBtnForProduct(String productName) {
        String formattedName = formatProductName(productName);
        return driver.findElement(By.id("remove-" + formattedName));
    }

    public WebElement getRemoveBtn() {
        return removeButton;
    }

    private String formatProductName(String productName) {
        return productName
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", "-");
    }

    public WebElement getShoppingCartBadge() {
        return wait.until(ExpectedConditions.visibilityOf(shoppingCartBadge));
    }

    public boolean isShoppingCartBadgeVisible() {
        try {
            return shoppingCartBadge.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void waitForShoppingCartBadgeToDisappear() {
        wait.until(ExpectedConditions.invisibilityOf(shoppingCartBadge));
    }
}

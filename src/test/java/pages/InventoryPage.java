package pages;

import framework.core.Element;
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

    private WebElement getAddToCartButton(String productName) {
        String formattedName = formatProductName(productName);
        return driver.findElement(By.id("add-to-cart-" + formattedName));
    }

    public void clickAddToCartButton(String productName) {
        new Element(getAddToCartButton(productName))
                .waitForClickable()
                .click();
    }

    public void clickAddToCartButton() {
        new Element(addToCartButton)
                .waitForClickable()
                .click();
    }

    private WebElement getRemoveBtnForProduct(String productName) {
        String formattedName = formatProductName(productName);
        return driver.findElement(By.id("remove-" + formattedName));
    }

    public void clickRemoveButtonForProduct(String productName) {
        new Element(getRemoveBtnForProduct(productName))
                .waitForClickable()
                .click();
    }

    public String getRemoveButtonTextForProduct(String productName) {
        return new Element(getRemoveBtnForProduct(productName))
                .waitForVisible()
                .getText();
    }

    public String getRemoveButtonText() {
        return new Element(removeButton)
                .waitForVisible()
                .getText();
    }

    public String getAddToCartButtonText(String productName) {
        return new Element(getAddToCartButton(productName))
                .waitForVisible()
                .getText();
    }

    private String formatProductName(String productName) {
        return productName
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", "-");
    }

    private WebElement getShoppingCartBadge() {
        return wait.until(ExpectedConditions.visibilityOf(shoppingCartBadge));
    }

    public String getShoppingCartBadgeText() {
        return new Element(getShoppingCartBadge()).getText();
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

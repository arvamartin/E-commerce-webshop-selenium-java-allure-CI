package pages;


import framework.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;

    public List<String> getCartItemNameTexts() {
        waitForCartItems();
        return cartItemNames.stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getCartItemPriceTexts() {
        waitForCartItems();
        return cartItemPrices.stream()
                .map(WebElement::getText)
                .toList();
    }

    private void waitForCartItems(){
        wait.until(driver -> !cartItemNames.isEmpty()
                && !cartItemPrices.isEmpty()
                && cartItemNames.size() == cartItemPrices.size());
    }
}

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

    public List<WebElement> getCartItemNames() {
        return cartItemNames;
    }

    public List<WebElement> getCartItemPrices() {
        return cartItemPrices;
    }
}

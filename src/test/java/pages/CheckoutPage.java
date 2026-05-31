package pages;

import framework.core.BasePage;
import framework.core.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CheckoutPage extends BasePage {

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> checkoutItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> checkoutItemPrices;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeText;

    public void clickCheckout() {
        element(checkoutButton).waitForClickable().click();
    }

    public void fillCustomerInformation(String firstName, String lastName, String postalCode) {
        element(firstNameInput).waitForVisible().clearAndType(firstName);
        element(lastNameInput).waitForVisible().clearAndType(lastName);
        element(postalCodeInput).waitForVisible().clearAndType(postalCode);
    }

    public void clickContinue() {
        element(continueButton).waitForClickable().click();
    }

    public void clickFinish() {
        element(finishButton).waitForClickable().click();
    }

    public List<String> getCheckoutItemNames() {
        waitForCheckoutItems();
        return checkoutItemNames.stream().map(WebElement::getText).toList();
    }

    public List<String> getCheckoutItemPrices() {
        waitForCheckoutItems();
        return checkoutItemPrices.stream().map(WebElement::getText).toList();
    }

    public String getItemTotalText() {
        return element(itemTotalLabel).waitForVisible().getText();
    }

    public String getTaxText() {
        return element(taxLabel).waitForVisible().getText();
    }

    public String getTotalText() {
        return element(totalLabel).waitForVisible().getText();
    }

    public String getCompleteHeaderText() {
        return element(completeHeader).waitForVisible().getText();
    }

    public String getCompleteText() {
        return element(completeText).waitForVisible().getText();
    }

    private void waitForCheckoutItems() {
        wait.until(driver -> !checkoutItemNames.isEmpty()
                && !checkoutItemPrices.isEmpty()
                && checkoutItemNames.size() == checkoutItemPrices.size());
    }

    private Element element(WebElement webElement) {
        return new Element(webElement);
    }
}

package actions;

import io.qameta.allure.Step;
import pages.CheckoutPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class CheckoutAction extends BaseAction<CheckoutAction> {
    private static final String ORDER_COMPLETE_HEADER = "Thank you for your order!";
    private final CheckoutPage checkoutPage;

    public CheckoutAction() {
        this.checkoutPage = new CheckoutPage();
    }

    @Step("Starts checkout from cart")
    public CheckoutAction startCheckout() {
        checkoutPage.clickCheckout();
        return this;
    }


    @Step("Fills customer information")
    public CheckoutAction fillCustomerInformation(String firstName, String lastName, String postalCode) {
        requireValue(firstName, "firstName");
        requireValue(lastName, "lastName");
        requireValue(postalCode, "postalCode");

        checkoutPage.fillCustomerInformation(firstName, lastName, postalCode);
        return this;
    }

    @Step("Continues to checkout overview")
    public CheckoutAction continueToOverview() {
        checkoutPage.clickContinue();
        return this;
    }

    @Step("Validates checkout overview")
    public CheckoutAction validateOverviewContainsProducts(String... expectedProductNames) {
        List<String> names = checkoutPage.getCheckoutItemNames();
        List<String> prices = checkoutPage.getCheckoutItemPrices();

        assertThat("Checkout names/prices are misaligned", names.size(), equalTo(prices.size()));
        for (String expectedProductName : expectedProductNames) {
            assertThat("Missing checkout item: " + expectedProductName, names.contains(expectedProductName), equalTo(true));
        }
        assertThat("Checkout overview should contain at least one product", names.size(), greaterThanOrEqualTo(1));

        BigDecimal itemTotal = prices.stream()
                .map(this::parsePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal tax = parseLabeledPrice(checkoutPage.getTaxText());
        BigDecimal total = parseLabeledPrice(checkoutPage.getTotalText());

        assertThat(checkoutPage.getItemTotalText(), containsString(formatPrice(itemTotal)));
        assertThat("Summary total does not equal item total plus tax", total, equalTo(itemTotal.add(tax).setScale(2, RoundingMode.HALF_UP)));
        return this;
    }

    @Step("Completes order")
    public CheckoutAction finishOrder() {
        checkoutPage.clickFinish();
        return this;
    }

    @Step("Validates completed order confirmation")
    public CheckoutAction validateOrderComplete() {
        assertThat(checkoutPage.getCompleteHeaderText(), equalTo(ORDER_COMPLETE_HEADER));
        assertThat(checkoutPage.getCompleteText(), containsString("Your order has been dispatched"));
        return this;
    }

    private BigDecimal parseLabeledPrice(String labeledPrice) {
        int dollarIndex = labeledPrice.indexOf('$');
        if (dollarIndex < 0) {
            throw new IllegalArgumentException("Price label does not contain a dollar amount: " + labeledPrice);
        }
        return parsePrice(labeledPrice.substring(dollarIndex));
    }

    private BigDecimal parsePrice(String price) {
        return new BigDecimal(price.replace("$", "").trim()).setScale(2, RoundingMode.HALF_UP);
    }

    private String formatPrice(BigDecimal price) {
        return "$" + price.setScale(2, RoundingMode.HALF_UP);
    }

    private void requireValue(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank.");
        }
    }
}

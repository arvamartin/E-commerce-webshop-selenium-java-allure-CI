package framework.core;

public enum Constants {

    // Url-s
    LOGIN_URL("https://www.saucedemo.com/"),
    HOME_PAGE_URL("https://www.saucedemo.com/inventory.html"),
    CART_PAGE_URL("https://www.saucedemo.com/cart.html"),
    CHECKOUT_STEP_ONE_URL("https://www.saucedemo.com/checkout-step-one.html"),
    CHECKOUT_STEP_TWO_URL("https://www.saucedemo.com/checkout-step-two.html"),
    CHECKOUT_COMPLETE_URL("https://www.saucedemo.com/checkout-complete.html"),
    INVENTORY_ITEM_PAGE_URL("https://www.saucedemo.com/inventory-item.html?id="),
    ABOUT_PAGE_URL("https://saucelabs.com/"),

    // Users
    STANDARD_USER("standard_user"),
    STANDARD_PASSWORD("secret_sauce");


    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}

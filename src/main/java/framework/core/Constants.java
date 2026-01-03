package framework.core;

public enum Constants {

    // Url-s
    LOGIN_URL("https://www.saucedemo.com/"),
    HOME_PAGE_URL("https://www.saucedemo.com/inventory.html"),
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

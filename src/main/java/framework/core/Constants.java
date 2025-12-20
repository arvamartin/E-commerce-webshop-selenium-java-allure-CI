package framework.core;

public enum Constants {

    // Url-s
    LOGIN_URL("https://www.saucedemo.com/"),
    HOME_PAGE_URL("https://www.saucedemo.com/inventory.html"),

    // Users
    STANDARD_USER("standard_user"),
    STANDARD_PASSWORD("secret_sauce"),


    // Property path
    UI_PROPERTIES_PATH("loginUi.properties");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}

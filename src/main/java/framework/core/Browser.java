package framework.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Browser {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final String CONFIG_FILE = "Config.properties";
    private static final String BROWSER_PROPERTY_KEY = "browser";
    private static final String HEADLESS_PROPERTY_KEY = "headless";
    private static final String HEADLESS_ENV_KEY = "HEADLESS";
    private static final String BROWSER_ENV_KEY = "BROWSER";

    private Browser() {
    }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            initializeDriver();
        }
        return DRIVER.get();
    }

    public static WebDriver peekDriver() {
        return DRIVER.get();
    }

    private static void initializeDriver() {
        BrowserType browserType = resolveBrowserType();
        switch (browserType) {
            case CHROME -> chromeDriver();
            case FIREFOX -> firefoxDriver();
            case EDGE -> edgeDriver();
        }

        if (DRIVER.get() == null) {
            throw new IllegalStateException("WebDriver was not initialized");
        }

        if (!isHeadless()) {
            DRIVER.get().manage().window().maximize();
        }
    }

    public static void quitDriver() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }

    public static void chromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", passwordManagerPreferences());

        if (isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        DRIVER.set(new ChromeDriver(options));
    }

    public static void firefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        DRIVER.set(new FirefoxDriver(options));
    }

    public static void edgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", passwordManagerPreferences());

        if (isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        DRIVER.set(new EdgeDriver(options));
    }

    private static Map<String, Object> passwordManagerPreferences() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        return prefs;
    }

    private static BrowserType resolveBrowserType() {
        String systemBrowser = System.getProperty(BROWSER_PROPERTY_KEY);
        if (systemBrowser != null && !systemBrowser.isBlank()) {
            return BrowserType.from(systemBrowser);
        }

        String envBrowser = System.getenv(BROWSER_ENV_KEY);
        if (envBrowser != null && !envBrowser.isBlank()) {
            return BrowserType.from(envBrowser);
        }

        String configBrowser = PropertyReader.getConfigValue(CONFIG_FILE, BROWSER_PROPERTY_KEY);
        if (configBrowser != null && !configBrowser.isBlank()) {
            return BrowserType.from(configBrowser);
        }

        return BrowserType.CHROME;
    }

    private static boolean isHeadless() {
        String systemProperty = System.getProperty(HEADLESS_PROPERTY_KEY);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return parseBoolean(systemProperty);
        }

        String envValue = System.getenv(HEADLESS_ENV_KEY);
        if (envValue != null && !envValue.isBlank()) {
            return parseBoolean(envValue);
        }

        return false;
    }

    private static boolean parseBoolean(String value) {
        return "true".equalsIgnoreCase(value)
                || "1".equals(value)
                || "yes".equalsIgnoreCase(value);
    }

    private enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE;

        private static BrowserType from(String browserValue) {
            try {
                return BrowserType.valueOf(browserValue.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(
                        "Unsupported browser '" + browserValue + "'. Supported values: chrome, firefox, edge",
                        ex
                );
            }
        }
    }
}

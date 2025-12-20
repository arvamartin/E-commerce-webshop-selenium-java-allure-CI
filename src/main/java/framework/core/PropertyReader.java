package framework.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static framework.core.Constants.UI_PROPERTIES_PATH;

public class PropertyReader {

    private static Properties properties = new Properties();

    static {
        try (InputStream fis = PropertyReader.class.getClassLoader().getResourceAsStream(UI_PROPERTIES_PATH.getValue())) {
            if (fis != null) {
                properties.load(fis);
            } else {
                throw new RuntimeException("Could not find property file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load property file", e);
        }
    }

    public static String getValue(String section, String key) {
        String prefixedKey = section + "." + key;

        String systemProperty = System.getProperty(prefixedKey);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }
        return properties.getProperty(prefixedKey);
    }
}
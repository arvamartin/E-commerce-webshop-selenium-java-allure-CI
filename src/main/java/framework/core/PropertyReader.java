package framework.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyReader {

    private static Properties properties = new Properties();

    public static void loadProperty(String path) {
        try (InputStream fis = PropertyReader.class.getClassLoader().getResourceAsStream(path)) {
            if (fis != null) {
                properties.load(fis);
            } else {
                throw new RuntimeException("Could not find property file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load property file", e);
        }
    }

    public static String getValue(String fileName, String section, String key) {
        loadProperty("UIData/" + fileName + ".properties");
        String prefixedKey = section + "." + key;

        String systemProperty = System.getProperty(prefixedKey);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }
        return properties.getProperty(prefixedKey);
    }

    public static String getConfigValue(String filename, String value) {
        loadProperty(filename);
        String property = System.getProperty(value);
        if (property != null && !property.isEmpty()) {
            return property;
        }
        return properties.getProperty(value);
    }
}
package framework.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


public class PropertyReader {
    private static final Map<String, Properties> PROPERTIES_CACHE = new ConcurrentHashMap<>();

    private static Properties loadProperty(String path) {
        return PROPERTIES_CACHE.computeIfAbsent(path, PropertyReader::readProperties);
    }

    private static Properties readProperties(String path) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException("Could not find property file: " + path);
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Could not load property file: " + path, e);
        }
    }

    public static String getValue(String fileName, String section, String key) {
        Properties properties = loadProperty("UIData/" + fileName + ".properties");
        String prefixedKey = section + "." + key;

        String systemProperty = System.getProperty(prefixedKey);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }
        return properties.getProperty(prefixedKey);
    }

    public static String getConfigValue(String filename, String value) {
        Properties properties = loadProperty(filename);
        String property = System.getProperty(value);
        if (property != null && !property.isEmpty()) {
            return property;
        }
        return properties.getProperty(value);
    }
}

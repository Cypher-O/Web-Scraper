// ConfigLoader.java
package com.synth.webscraper.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;

    public ConfigLoader(String configPath) throws IOException {
        properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(configPath)) {
            if (inputStream == null) {
                throw new IOException("Unable to find config file: " + configPath);
            }
            properties.load(inputStream);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
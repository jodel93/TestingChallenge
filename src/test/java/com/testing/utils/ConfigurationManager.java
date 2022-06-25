package com.testing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static ConfigurationManager manager;
    private static final Properties properties = new Properties();

    private ConfigurationManager() throws IOException {
        String filePath = new File("").getAbsolutePath().concat("\\src\\test\\resources\\properties\\config.properties");
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
    }

    public static ConfigurationManager getInstance() {
        if (manager == null){
            synchronized (ConfigurationManager.class){
                try {
                    manager = new ConfigurationManager();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return manager;
    }

    public String getConfiguration(String key){
        return System.getProperty(key, properties.getProperty(key));
    }
}

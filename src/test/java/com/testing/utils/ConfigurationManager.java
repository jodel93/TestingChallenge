package com.testing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static ConfigurationManager _manager;
    private static final Properties _properties = new Properties();

    private ConfigurationManager() throws IOException {
        String filePath = new File("").getAbsolutePath().concat("\\src\\test\\resources\\config.properties");
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        _properties.load(inputStream);
    }

    public static ConfigurationManager getInstance() {
        if (_manager == null){
            synchronized (ConfigurationManager.class){
                try {
                    _manager = new ConfigurationManager();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return _manager;
    }

    public String getConfiguration(String key){
        return System.getProperty(key, _properties.getProperty(key));
    }
}
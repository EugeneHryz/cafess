package com.eugene.cafe.manager;

import java.util.ResourceBundle;

public class ResourceManager {

    private final ResourceBundle bundle;

    public ResourceManager(String fileName) {
        bundle = ResourceBundle.getBundle(fileName);
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}

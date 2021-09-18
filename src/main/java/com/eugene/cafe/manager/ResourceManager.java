package com.eugene.cafe.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {

    private final ResourceBundle bundle;

    public ResourceManager(String fileName, Locale locale) {
        bundle = ResourceBundle.getBundle(fileName, locale);
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}

package by.epam.service;

import java.util.ResourceBundle;

public class ConfigManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");
    private final static ResourceBundle messageBundle = ResourceBundle.getBundle("resources/messages");

    private ConfigManager() {
    }

    // Get config property
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    // Get messages
    public static String message(String key) {
        return messageBundle.getString(key);
    }


}

package by.epam.utils.manager;

import java.util.ResourceBundle;

public class Manager {
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources/pages");
    private final static ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle("resources/messages");

    private Manager() {
    }

    // Get config property
    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

    // Get messages
    public static String message(String key) {
        return MESSAGE_BUNDLE.getString(key);
    }


}

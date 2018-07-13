package by.epam.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/messages");
    private MessageManager() {}
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
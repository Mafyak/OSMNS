package by.epam.utils.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class Manager {

    private final static ResourceBundle PAGE_BUNDLE = ResourceBundle.getBundle("resources/pages");
    private final static ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle("resources/messages");
    private final static ResourceBundle CONFIG_BUNDLE = ResourceBundle.getBundle("resources/config");
    private static volatile Manager instance;

    private Manager() {
    }

    public static Manager getMan() {
        Manager localInstance = instance;
        if (localInstance == null) {
            synchronized (Manager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Manager();
                }
            }
        }
        return localInstance;
    }

    // Get page
    public String getPage(String key) {
        return PAGE_BUNDLE.getString(key);
    }

    // Get config
    public String getConfig(String key) {
        return CONFIG_BUNDLE.getString(key);
    }

    // Get messages
    public String message(String key) {
        return MESSAGE_BUNDLE.getString(key);
    }

    // Get messages
    public String message(String key, Locale locale) {
        return ResourceBundle.getBundle("resources/content", locale).getString(key);
    }
}

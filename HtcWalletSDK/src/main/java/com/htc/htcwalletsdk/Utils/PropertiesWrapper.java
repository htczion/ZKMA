package com.htc.htcwalletsdk.Utils;

public class PropertiesWrapper {
    /**
     * SystemProperties getString() function
     * @param id
     * @param defaultValue
     * @return String result
     */
    public static String getString(String id, String defaultValue) {
        String property = System.getProperty(id, defaultValue);
        return property;
    }

    /**
     * SystemProperties getInt() function
     * @param id
     * @param defaultValue
     * @return int result
     */
    public static int getInt(String id, String defaultValue) {
        String property = System.getProperty(id, defaultValue);
        return Integer.parseInt(property);
    }

    /**
     * SystemProperties getBoolean() function
     * @param id
     * @param defaultValue
     * @return boolean result
     */
    public static boolean getBoolean(String id, String defaultValue) {
        String property = System.getProperty(id, defaultValue);
        return Boolean.parseBoolean(property);
    }

    public static String setString(String id, String defaultValue) {
        String previous_property = System.setProperty(id, defaultValue);
        return previous_property;
    }

    public static int setInt(String id, int defaultValue) {
        String property = System.setProperty(id, Integer.toString(defaultValue));
        return Integer.parseInt(property);
    }

    public static boolean setBoolean(String id, boolean defaultValue) {
        String property = System.getProperty(id, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(property);
    }
}
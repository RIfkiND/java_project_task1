package com.rifki.java_project_task1.config;

import java.util.Locale;

import com.rifki.java_project_task1.config.AppConfig.ConfigLoader;

public class DatabaseConfig {

    private DatabaseConfig() {}

    private static String getDbProperty(String key, String defaultValue) {
        return ConfigLoader.getProperty(key, defaultValue);
    }

    public static String getActiveDatabase() {
        return getDbProperty(
            Constants.ACTIVE_DB_KEY,
            Constants.DEFAULT_ACTIVE_DB
        )
            .trim()
            .toLowerCase(Locale.ROOT);
    }

    public static boolean isMySqlActive() {
        return "mysql".equals(getActiveDatabase());
    }

    public static String getMySqlUrl() {
        return getDbProperty(
            Constants.MYSQL_URL_KEY,
            Constants.DEFAULT_MYSQL_URL
        );
    }

    public static String getMySqlUsername() {
        return getDbProperty(
            Constants.MYSQL_USERNAME_KEY,
            Constants.DEFAULT_MYSQL_USERNAME
        );
    }

    public static String getMySqlPassword() {
        return getDbProperty(
            Constants.MYSQL_PASSWORD_KEY,
            Constants.DEFAULT_MYSQL_PASSWORD
        );
    }

    public static String getMySqlDriver() {
        return getDbProperty(
            Constants.MYSQL_DRIVER_KEY,
            Constants.DEFAULT_MYSQL_DRIVER
        );
    }
    public static String getActiveUrl() {
        return isMySqlActive() ? getMySqlUrl() : null;
    }

    public static String getActiveUsername() {
        return isMySqlActive() ? getMySqlUsername() : null;
    }

    public static String getActivePassword() {
        return isMySqlActive() ? getMySqlPassword() : null;
    }

    public static String getActiveDriver() {
        return isMySqlActive() ? getMySqlDriver() : null;
    }
}
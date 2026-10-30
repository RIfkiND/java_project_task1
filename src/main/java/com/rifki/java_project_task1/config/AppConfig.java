package com.rifki.java_project_task1.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.rifki.java_project_task1.config.Constants;

public class AppConfig {

    private AppConfig() {}

    public static String get(String key, String defaultValue) {
        return ConfigLoader.getProperty(key, defaultValue);
    }

    public static String getAppName() {
        return get(Constants.APP_NAME_KEY, Constants.DEFAULT_APP_NAME);
    }

    public static String getAppVersion() {
        return get(Constants.APP_VERSION_KEY, Constants.DEFAULT_APP_VERSION);
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    public static class ConfigLoader {

        private static final Properties properties = new Properties();
        private static Dotenv dotenv;

        static {
            try (
                InputStream in = ConfigLoader.class.getResourceAsStream(
                    "/config/application.properties"
                )
            ) {
                if (in != null) {
                    properties.load(in);
                }
            } catch (IOException ignored) {}

            try {
                dotenv = Dotenv.configure()
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();
            } catch (Exception e) {
                System.out.print("Errror On" + e);
            }
        }

        public static String getProperty(String key) {
            if (dotenv != null) {
                String envVal = dotenv.get(key.toUpperCase().replace(".", "_"));
                if (envVal != null) return envVal;
            }

            return properties.getProperty(key);
        }

        public static String getProperty(String key, String defaultValue) {
            String val = getProperty(key);
            return val != null ? val : defaultValue;
        }
    }
}

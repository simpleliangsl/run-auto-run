package org.simple.autorun.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by sliang on 9/27/17.
 */
public class Config {

    private static Properties properties;

    public static String getProperty(String key) {
        if (properties == null) {
            String[] fileNames = {"auto-run-base.properties", System.getProperty("customProperties")};
            properties = loadProperties(fileNames);
        }

        return System.getProperty(key, properties.getProperty(key));
    }

    public static boolean isGroupEnabled(String groupName) {
        String groupFilter = getProperty("groupFilter");
        return groupFilter.equals("*") || groupFilter.contains(groupName);
    }

    public static Properties loadProperties(String[] fileNames) {
        Properties result = new Properties();

        for (String fileName : fileNames) {
            if (fileName != null && !fileName.isEmpty()) {
                result.putAll(loadProperties(fileName));
            }
        }

        return result;
    }

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        InputStream inputStream;

        try {
            inputStream = Config.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null) {
                properties.load(reader);
            } else {
                throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
            }
        }
        catch(Exception e) {
            LogWrapper.current().fatal(e);
        }

        return properties;
    }
}

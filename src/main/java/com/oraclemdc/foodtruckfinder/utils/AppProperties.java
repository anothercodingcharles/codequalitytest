package com.oraclemdc.foodtruckfinder.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


//This utility class reads in properties file
public class AppProperties {

    private static Properties p;

    public static String getProperty(String property) {

        if (p == null)
            p = getProperties();
        return p != null ? p.getProperty(property) : null;
    }

    private static Properties getProperties() {
        FileReader reader;
        Properties p = null;
        try {
            reader = new FileReader("src/main/resources/app.properties");
            p = new Properties();
            p.load(reader);
        } catch (IOException e) {
            System.out.println("Missing properties fle " + e.getMessage());
            return null;
        }
        return p;

    }
}

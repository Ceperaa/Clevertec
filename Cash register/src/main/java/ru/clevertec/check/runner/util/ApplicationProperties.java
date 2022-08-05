package ru.clevertec.check.runner.util;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final String PATH = "E:\\Clevertec\\Cash register\\src\\main\\resources\\application.properties";


    @SneakyThrows(IOException.class)
    public static String getByKey(String key) {
        InputStream stream = new FileInputStream(PATH);
        PROPERTIES.load(stream);
        return PROPERTIES.getProperty(key);
    }
}

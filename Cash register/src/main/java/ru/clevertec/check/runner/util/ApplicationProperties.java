package ru.clevertec.check.runner.util;

import lombok.SneakyThrows;
import ru.clevertec.check.runner.util.mapper.PdfMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();

    @SneakyThrows(IOException.class)
    public static String getByKey(String key) {
        InputStream resourceAsStream = PdfMapper.class
                .getClassLoader().getResourceAsStream("application.properties");
        PROPERTIES.load(resourceAsStream);
        return PROPERTIES.getProperty(key);
    }
}

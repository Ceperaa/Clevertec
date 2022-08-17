package ru.clevertec.check.runner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import ru.clevertec.check.runner.util.entity.ApplicationYaml;

import java.io.InputStream;

public class ApplicationProperties {

    @SneakyThrows
    public static ApplicationYaml getProperty() {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("application.yaml");
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.coercionConfigFor(ApplicationYaml.class);
        ApplicationYaml employee = om.readValue(resourceAsStream, ApplicationYaml.class);
        return employee;
    }
}

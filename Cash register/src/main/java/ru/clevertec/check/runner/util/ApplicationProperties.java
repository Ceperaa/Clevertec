package ru.clevertec.check.runner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import ru.clevertec.check.runner.model.DataSourceYaml;

import java.io.InputStream;

public class ApplicationProperties {

    @SneakyThrows
    public static DataSourceYaml getDataSource() {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("application.yaml");
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.coercionConfigFor(DataSourceYaml.class);
        DataSourceYaml employee = om.readValue(resourceAsStream, DataSourceYaml.class);
        return employee;
    }
}

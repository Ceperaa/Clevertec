package ru.clevertec.check.runner.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

public class ApplicationProperties implements PropertySourceFactory {

    private static Properties properties;

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource.getResource());
        Properties properties = Objects.requireNonNull(yamlPropertiesFactoryBean.getObject());
        ApplicationProperties.properties = properties;
        return new PropertiesPropertySource("application", properties);
    }

    public static Properties getProp(){
        return properties;
    }
}
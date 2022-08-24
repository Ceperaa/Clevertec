package ru.clevertec.check.runner.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.clevertec.check.runner.util.ApplicationProperties;
import ru.clevertec.check.runner.util.entity.ApplicationYaml;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@SuppressWarnings("ALL")
@Configuration
@ComponentScan({"ru.clevertec.check.runner"})
@PropertySource("classpath:application.yaml")
public class AppConfig {

    private final ApplicationYaml property = ApplicationProperties.getProperty();

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(property.getLiquibase().getChangelog());
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(property.getDatasource().getDriver());
        dataSource.setUrl(property.getDatasource().getUrl());
        dataSource.setUsername(property.getDatasource().getUsername());
        dataSource.setPassword(property.getDatasource().getPassword());
        return dataSource;
    }

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}

package ru.clevertec.check.runner.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.clevertec.check.runner.model.DataSourceYaml;
import ru.clevertec.check.runner.util.ApplicationProperties;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@SuppressWarnings("ALL")
@Configuration
@ComponentScan({"ru.clevertec.check.runner"})
@PropertySource("classpath:application.properties")
public class AppConfig {

    private final String CHENGLOG = "classpath:db-changelog-1.xml";

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CHENGLOG);
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        DataSourceYaml dataSource1 = ApplicationProperties.getDataSource();
        dataSource.setDriverClassName(dataSource1.getDriver());
        dataSource.setUrl(dataSource1.getUrl());
        dataSource.setUsername(dataSource1.getUsername());
        dataSource.setPassword(dataSource1.getPassword());
        return dataSource;
    }

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}

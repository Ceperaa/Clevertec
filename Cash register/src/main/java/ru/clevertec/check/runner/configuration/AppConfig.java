package ru.clevertec.check.runner.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.SQLConnector;
import ru.clevertec.check.runner.util.ApplicationProperties;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@SuppressWarnings("ALL")
@Configuration
@ComponentScan({"ru.clevertec.check.runner"})
@PropertySource(value = "classpath:application.yaml", factory = ApplicationProperties.class)
public class AppConfig {

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.driver}")
    private String driver;
    @Value("${datasource.username}")
    private String user;
    @Value("${datasource.password}")
    private String password;
    @Value("${liquibase.changelog}")
    private String changelog;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelog);
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Scope("prototype")
    public SQLConnector sqlConnector() {
        return new SQLConnector(url, driver, user, password);
    }

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}

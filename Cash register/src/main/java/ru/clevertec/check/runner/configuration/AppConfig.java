package ru.clevertec.check.runner.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.check.runner.util.ApplicationProperties;

import java.util.Properties;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.clevertec.check.runner.repository")
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

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.format_sql}")
    private String formatSql;

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
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return jpaTransactionManager;
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("ru.clevertec.check.runner");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        em.setJpaProperties(properties);
        em.setDataSource(dataSource);
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }
}

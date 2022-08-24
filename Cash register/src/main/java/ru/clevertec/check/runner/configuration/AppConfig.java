package ru.clevertec.check.runner.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.check.runner.util.ApplicationProperties;
import ru.clevertec.check.runner.util.entity.ApplicationYaml;

import javax.validation.Valid;
import java.util.Properties;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@SuppressWarnings("ALL")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.clevertec.check.runner.repository.jpa")
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
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return jpaTransactionManager;
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("ru.clevertec.check.runner");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", property.getHibernate().getDialect());
        properties.put("hibernate.show_sql", property.getHibernate().getShow_sql());
        em.setJpaProperties(properties);
        em.setDataSource(dataSource);
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}

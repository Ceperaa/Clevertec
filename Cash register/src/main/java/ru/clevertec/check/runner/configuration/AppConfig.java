package ru.clevertec.check.runner.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.clevertec.check.runner.util.ApplicationProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@SuppressWarnings("ALL")
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan({"ru.clevertec.check.runner"})
@PropertySource("classpath:application.properties")
@Import(value = {RepositoryFactory.class})
public class AppConfig implements WebMvcConfigurer {

    private final String URL = ApplicationProperties.getByKey("datasource.url");
    private final String DRIVER_CLASS_NAME = ApplicationProperties.getByKey("datasource.driver-class-name");
    private final String USER = ApplicationProperties.getByKey("datasource.username");
    private final String PASSWORD = ApplicationProperties.getByKey("datasource.password");
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
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(testApiInfo());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**");
    }

    private ApiInfo testApiInfo() {
        return new ApiInfoBuilder()
                .title("") // Заголовок
                .description("MVC application \n")
                .version("1.0 version")
                .build();
    }
}

package ru.clevertec.check.runner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.clevertec.check.runner.services.CheckRunnerServices;
import ru.clevertec.check.runner.services.DiscountCardServices;
import ru.clevertec.check.runner.services.impl.ProductServicesImpl;

import static org.mockito.Mockito.mock;

@EnableWebMvc
@Configuration
@ComponentScan({"ru.clevertec.check.runner.controller"})
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MockMvc mockMvc(WebApplicationContext webApplicationContext) {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Bean
    public ProductController productController() {
        return new ProductController(mock(ProductServicesImpl.class));
    }

    @Bean
    public DiscountCardController discountCardController(){
        return new DiscountCardController(mock(DiscountCardServices.class));
    }

    @Bean CheckRunnerController checkRunnerController(){
        return new CheckRunnerController(mock(CheckRunnerServices.class));
    }
}

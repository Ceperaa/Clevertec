package ru.clevertec.check.runner.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.streamIO.impl.DiscountCardIO;
import ru.clevertec.check.runner.streamIO.impl.ProductIO;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main config class for Cash register app
 *
 * @author Sergey Degtyarev
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan({"ru.clevertec.check.runner"})
@PropertySource("classpath:increment.properties")
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public Map<Long, Product> productMap(ProductIO productIO) throws Exception {
        Map<Long, Product> productMap = new HashMap<>();
//        productMap.put(1L, new Product(1, "Product1", 3, 13, 23));
//        productMap.put(2L, new Product(2, "Product2", 66, 4, 10));
//        productMap.put(3L, new Product(3, "Product3", 45, 0, 20));
//        productMap.put(4L, new Product(4, "Product4", 23, 6, 30));
//        productMap.put(5L, new Product(5, "Product5", 65, 0, 45));
//        productMap.put(6L, new Product(6, "Product6", 76, 4, 75));
//        productMap.put(7L, new Product(7, "Product7", 23, 2, 80));
//        productMap.put(8L, new Product(8, "Product8", 32, 10, 50));
//        productMap.put(9L, new Product(9, "Product9", 43, 0, 5));
//        productMap.put(10L, new Product(10, "Product10", 76, 6, 100));
//        productIO.exportServiceFile(new ArrayList<>(productMap.values()),true );

        productIO.importServiceFile()
                .stream()
                .map(p -> (Product)p)
                .map(product -> productMap.put(product.getId(),product)).collect(Collectors.toList());
        return productMap;
    }

    @Bean
    public Map<Long, ProductInformation> productInformationMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Check> checkMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, DiscountCard> discountCardMap(DiscountCardIO discountCardIO) throws Exception {
        Map<Long, DiscountCard> discountCardMap = new HashMap<>();
        discountCardIO.importServiceFile()
                .stream()
                .map(p -> (DiscountCard)p)
                .map(discountCard -> discountCardMap.put(discountCard.getId(),discountCard)).collect(Collectors.toList());
        return discountCardMap;
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

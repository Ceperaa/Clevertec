package ru.clevertec.check.runner.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.jdbc.CheckRepository;
import ru.clevertec.check.runner.repository.impl.jdbc.DiscountCardRepository;
import ru.clevertec.check.runner.repository.impl.jdbc.ProductInformationRepository;
import ru.clevertec.check.runner.repository.impl.jdbc.ProductRepository;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.EntityManager;
import ru.clevertec.check.runner.repository.impl.streamio.CheckRepositoryImpl;
import ru.clevertec.check.runner.repository.impl.streamio.DiscountCardRepositoryImpl;
import ru.clevertec.check.runner.repository.impl.streamio.ProductInformationRepositoryImpl;
import ru.clevertec.check.runner.repository.impl.streamio.ProductRepositoryImpl;
import ru.clevertec.check.runner.streamIO.IStreamIO;
import ru.clevertec.check.runner.streamIO.impl.DiscountCardIO;
import ru.clevertec.check.runner.streamIO.impl.ProductIO;
import ru.clevertec.check.runner.streamIO.impl.ProductInformationIO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class RepositoryFactory {

    private final static SomeRepo someRepo = SomeRepo.JDBC;

    @Bean
    public RepositoryEntity<Product> productRepository(
            EntityManager getConnection
            , Map<Long, Product> productMap
            , IStreamIO productIO
    ) {
       return choice(new ProductRepositoryImpl(productMap, productIO),new ProductRepository(getConnection));
    }

    @Bean
    public RepositoryEntity<DiscountCard> discountCardRepository(
            EntityManager getConnection
            , Map<Long, DiscountCard> cardMap
            , IStreamIO discountCardIO
    ) {
        return choice(new DiscountCardRepositoryImpl(cardMap, discountCardIO),new DiscountCardRepository(getConnection));
    }

    @Bean
    public RepositoryEntity<ProductInformation> productInformationRepository(
            EntityManager getConnection
            , Map<Long, ProductInformation> map
            , IStreamIO productInformationIO
    ) {
        return choice(new ProductInformationRepositoryImpl(map, productInformationIO),new ProductInformationRepository(getConnection));
    }

    @Bean
    public RepositoryEntity<Check> checkRepository(
            EntityManager getConnection
            , Map<Long, Check> map
            , IStreamIO checkIO
    ) {
        return choice(new CheckRepositoryImpl(map, checkIO),new CheckRepository(getConnection));
    }

    private RepositoryEntity choice(RepositoryEntity o1, RepositoryEntity o2){
        switch (someRepo) {
            case STREAMIO:
               return o1;
            default:
            case JDBC:
                return o2;
        }
    }

    @Bean
    public Map<Long, Product> productMap(ProductIO productIO) throws Exception {
        Map<Long, Product> productMap = new HashMap<>();
        productIO.importServiceFile()
                .stream()
                .map(p -> (Product)p)
                .map(product -> productMap.put(product.getId(),product)).collect(Collectors.toList());
        return productMap;
    }

    @Bean
    public Map<Long, ProductInformation> productInformationMap(ProductInformationIO productInformationIO) throws Exception {
        Map<Long, ProductInformation> productMap = new HashMap<>();
        productInformationIO.importServiceFile()
                .stream()
                .map(p -> (ProductInformation)p)
                .map(productInformation -> productMap.put(productInformation.getId(),productInformation)).collect(Collectors.toList());
        return productMap;
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
}

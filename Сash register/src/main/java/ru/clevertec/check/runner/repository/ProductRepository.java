package ru.clevertec.check.runner.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Product;

import java.util.Map;

@Repository
public class ProductRepository {

    private Map<Long, Product> productMap;

    public Product get(long id) {
        productMap.put(1L, new Product(1, "Product1", 3, 3, 23));
        productMap.put(2L, new Product(2, "Product2", 66, 0, 10));
        productMap.put(3L, new Product(3, "Product3", 45, 0, 20));
        productMap.put(4L, new Product(4, "Product4", 23, 6, 30));
        productMap.put(5L, new Product(5, "Product5", 65, 0, 45));
        productMap.put(6L, new Product(6, "Product6", 76, 4, 75));
        productMap.put(7L, new Product(7, "Product7", 23, 0, 80));
        productMap.put(8L, new Product(8, "Product8", 32, 10, 50));
        productMap.put(9L, new Product(9, "Product9", 43, 0, 5));
        productMap.put(10L, new Product(10, "Product10", 76, 6, 100));
        return productMap.get(id);
    }
}

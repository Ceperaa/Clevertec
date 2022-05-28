package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.ProductRepository;

import java.util.Map;

/**
 *
 * @author Sergey Degtyarev
 */

@Repository
public class ProductRepositoryImpl implements ProductRepository {

     private final Map<Long,Product> productMap;

    @Autowired
    public ProductRepositoryImpl(Map<Long, Product> productMap) {
        this.productMap = productMap;
    }

    public Product get(long id) {
        return productMap.get(id);
    }

    public void save(Product product){
        productMap.replace(product.getId(),product);
    }

}

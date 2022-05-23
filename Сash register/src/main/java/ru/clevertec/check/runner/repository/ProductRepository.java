package ru.clevertec.check.runner.repository;

import ru.clevertec.check.runner.model.Product;

import java.util.Map;

public class ProductRepository {

    private Map<Long,Product> productMap;

    public void add(Product product){
        productMap.put(product.getId(),product);
    }

    public Product get(long id){
       return productMap.get(id);
    }

    public void delete(long id){
        productMap.remove(id);
    }
}

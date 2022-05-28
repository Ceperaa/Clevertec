package ru.clevertec.check.runner.repository;

import ru.clevertec.check.runner.model.Product;

public interface ProductRepository {

    Product get(long id);

    void save(Product product);
}

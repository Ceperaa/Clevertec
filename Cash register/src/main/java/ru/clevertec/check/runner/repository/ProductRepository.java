package ru.clevertec.check.runner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.runner.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
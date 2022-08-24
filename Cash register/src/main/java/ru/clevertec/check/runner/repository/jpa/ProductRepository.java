package ru.clevertec.check.runner.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}

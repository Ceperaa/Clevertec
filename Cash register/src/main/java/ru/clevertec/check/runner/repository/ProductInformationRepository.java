package ru.clevertec.check.runner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.runner.model.entity.ProductInformation;

public interface ProductInformationRepository extends JpaRepository<ProductInformation,Long> {
}

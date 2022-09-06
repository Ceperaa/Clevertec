package ru.clevertec.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.model.entity.ProductInformation;

public interface ProductInformationRepository extends JpaRepository<ProductInformation,Long> {
}

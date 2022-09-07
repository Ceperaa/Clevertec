package ru.clevertec.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.model.entity.DiscountCard;

public interface DiscountCardRepository extends JpaRepository<DiscountCard,Long> {
}

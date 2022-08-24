package ru.clevertec.check.runner.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.runner.model.entity.DiscountCard;

public interface DiscountCardRepository extends JpaRepository<DiscountCard,Long>{
}

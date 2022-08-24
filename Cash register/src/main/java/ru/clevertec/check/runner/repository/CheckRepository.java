package ru.clevertec.check.runner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.runner.model.entity.Check;


public interface CheckRepository extends JpaRepository<Check,Long> {
}

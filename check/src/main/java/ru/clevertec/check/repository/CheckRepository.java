package ru.clevertec.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.check.model.entity.Check;

import java.time.LocalDateTime;

public interface CheckRepository extends JpaRepository<Check,Long> {

    //все записи старше недели
    @Modifying
    @Query("delete from Check where date < :dateTime ")
    void deleteCheck(@Param(value = "dateTime") LocalDateTime dateTime);
}

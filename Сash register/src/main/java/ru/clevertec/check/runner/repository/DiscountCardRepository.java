package ru.clevertec.check.runner.repository;

import ru.clevertec.check.runner.model.DiscountCard;

public interface DiscountCardRepository {

    DiscountCard get(long id);
}

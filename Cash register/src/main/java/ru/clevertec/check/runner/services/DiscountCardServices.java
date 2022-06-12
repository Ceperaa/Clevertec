package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.DiscountCard;

import java.util.List;

public interface DiscountCardServices {

    DiscountCard findById(Long id);

    List<DiscountCard> allListDiscountCard() throws Exception;

    DiscountCard saveCard(DiscountCard card) throws Exception;

    void deleteCard(long id) throws Exception;

}

package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;


public interface DiscountCardService {

    Optional<DiscountCard> findById(long id) ;

    List<DiscountCard> allListDiscountCard(int offset, Integer limit) ;

    DiscountCard saveCard(DiscountCardDtoForSave card) ;

    void deleteCard(long id) throws  ObjectNotFoundException;

    DiscountCard updateDiscountCard(DiscountCardDtoForSave card) ;

}

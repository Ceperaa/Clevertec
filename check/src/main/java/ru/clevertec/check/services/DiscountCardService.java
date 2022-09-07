package ru.clevertec.check.services;


import ru.clevertec.check.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.util.exception.ObjectNotFoundException;

import java.util.List;

public interface DiscountCardService {

    DiscountCard findById(long id) throws ObjectNotFoundException;

    List<DiscountCard> allListDiscountCard(int offset, Integer limit) ;

    DiscountCard saveCard(DiscountCardDtoForSave card) ;

    void deleteCard(long id) throws ObjectNotFoundException;

    DiscountCard updateDiscountCard(DiscountCard card) ;

}

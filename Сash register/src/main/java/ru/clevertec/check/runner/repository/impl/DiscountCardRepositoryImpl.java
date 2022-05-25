package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.DiscountCardRepository;

import java.util.Map;

@Repository
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private final Map<Long,DiscountCard> discountCardMap;

    @Autowired
    public DiscountCardRepositoryImpl(Map<Long, DiscountCard> discountCardMap) {
        this.discountCardMap = discountCardMap;
    }

    public DiscountCard get(long id){
        return discountCardMap.get(id);
    }
}

package ru.clevertec.check.runner.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.DiscountCard;

import java.util.Map;

@Repository
public class DiscountCardRepository {

    private Map<Long, DiscountCard> discountCardMap;

    public DiscountCard get(long id){
        discountCardMap.put(1L,new DiscountCard(1,10));
        discountCardMap.put(2L,new DiscountCard(2,20));
        discountCardMap.put(3L,new DiscountCard(3,2));
        discountCardMap.put(4L,new DiscountCard(4,3));
        discountCardMap.put(5L,new DiscountCard(5,4));
        discountCardMap.put(6L,new DiscountCard(6,5));
        discountCardMap.put(7L,new DiscountCard(7,5));
        discountCardMap.put(8L,new DiscountCard(8,5));
        discountCardMap.put(9L,new DiscountCard(9,5));
        discountCardMap.put(10L,new DiscountCard(10,5));
        return discountCardMap.get(id);
    }
}

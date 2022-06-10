package ru.clevertec.check.runner.services.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.impl.DiscountCardRepositoryImpl;

import java.util.List;

@Service
public class DiscountCardServicesImpl {

    private final DiscountCardRepositoryImpl discountCardRepo;

    public DiscountCardServicesImpl(DiscountCardRepositoryImpl discountCardRepo) {
        this.discountCardRepo = discountCardRepo;
    }

    public DiscountCard findById(long id){
        return discountCardRepo.findById(id);
    }

    public List<DiscountCard> allListDiscountCard() throws Exception {
        return discountCardRepo.findAll();
    }

    public DiscountCard saveCard(DiscountCard card) throws Exception {
        return discountCardRepo.add(card);
    }

    public DiscountCard update(DiscountCard card) throws Exception {
        return discountCardRepo.update(card);
    }

    public void deleteCard(long id) throws Exception {
        discountCardRepo.delete(id);
    }
}

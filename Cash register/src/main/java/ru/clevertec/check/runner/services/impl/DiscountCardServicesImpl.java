package ru.clevertec.check.runner.services.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.DiscountCardServices;

import java.util.List;

@Service
public class DiscountCardServicesImpl implements DiscountCardServices {

    private final RepositoryEntity<DiscountCard> discountCardRepo;

    public DiscountCardServicesImpl(RepositoryEntity<DiscountCard> discountCardRepo) {
        this.discountCardRepo = discountCardRepo;
    }

    public DiscountCard findById(Long id){
        return discountCardRepo.findById(id);
    }

    public List<DiscountCard> allListDiscountCard() throws Exception {
        return discountCardRepo.findAll();
    }

    public DiscountCard saveCard(DiscountCard card) throws Exception {
        return discountCardRepo.add(card);
    }

    public void deleteCard(long id) throws Exception {
        discountCardRepo.delete(id);
    }
}

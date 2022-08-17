package ru.clevertec.check.runner.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.EntityServiceCrud;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService, EntityServiceCrud<DiscountCard> {

    private final RepositoryEntity<DiscountCard> discountCardRepository;
    private final ModelMapper modelMapper;

    @Override
    public DiscountCard findById(Long id) throws ObjectNotFoundException {
        return discountCardRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(DiscountCard.class, id));
    }

    public List<DiscountCard> allList(int offset, int limit) {
        return discountCardRepository.findAll(limit, offset);
    }

    public DiscountCard update(Object card) {
        return discountCardRepository.update(modelMapper.map(card, DiscountCard.class));
    }

    public DiscountCard create(Object card) {
        return discountCardRepository.add(modelMapper.map(card, DiscountCard.class));
    }

    public void delete(long id) throws ObjectNotFoundException {
        discountCardRepository.delete(id);
    }

    @Override
    public DiscountCard findById(long id) {
       return findById(id);
    }
}

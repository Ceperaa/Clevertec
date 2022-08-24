package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.jpa.DiscountCardRepository;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.services.EntityServiceCrud;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository  discountCardRepository;
    private final ModelMapper modelMapper;

    @Override
    public DiscountCard findById(Long id) throws ObjectNotFoundException {
        return discountCardRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException(DiscountCard.class, id));
    }

    public List<DiscountCard> allList(int offset, int limit) {
        return discountCardRepository.findAll(PageRequest.of(offset, limit)).toList();
    }

    public DiscountCard update(Object card) {
        return discountCardRepository.save(modelMapper.map(card, DiscountCard.class));
    }

    public DiscountCard create(Object card) {
        return discountCardRepository.save(modelMapper.map(card, DiscountCard.class));
    }

    public void delete(long id) throws ObjectNotFoundException {
        discountCardRepository.deleteById(id);
    }

    @Override
    public DiscountCard findById(long id) {
       return findById(id);
    }
}

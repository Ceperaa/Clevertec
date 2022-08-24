package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final RepositoryEntity<DiscountCard> discountCardRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<DiscountCard> findById(long id){
        return discountCardRepository.findById(id);
    }

    @Override
    public List<DiscountCard> allListDiscountCard(int offset, Integer limit)  {
        return discountCardRepository.findAll(limit, offset);
    }

    @Override
    public DiscountCard saveCard(DiscountCardDtoForSave card) {
        return discountCardRepository.add(modelMapper.map(card, DiscountCard.class));
    }

    @Override
    public void deleteCard(long id) throws ObjectNotFoundException {
        discountCardRepository.delete(id);
    }

    @Override
    public DiscountCard updateDiscountCard(DiscountCardDtoForSave card) {
        return discountCardRepository.update(modelMapper.map(card, DiscountCard.class));
    }
}

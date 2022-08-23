package ru.clevertec.check.runner.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final RepositoryEntity<DiscountCard> discountCardRepository;
    private final ModelMapper modelMapper;

    public Optional<DiscountCard> findById(long id) {
        return discountCardRepository.findById(id);
    }

    public List<DiscountCard> allListDiscountCard(int offset, Integer limit) {
        return discountCardRepository.findAll(limit, offset);
    }

    public DiscountCard updateDiscountCard(DiscountCardDtoForSave card) {
        return discountCardRepository.update(modelMapper.map(card, DiscountCard.class));
    }

    public DiscountCard saveCard(DiscountCardDtoForSave card) {
        return discountCardRepository.add(modelMapper.map(card, DiscountCard.class));
    }

    public void deleteCard(long id) throws ObjectNotFoundException {
        DiscountCard discountCard = findById(id).orElseThrow(() -> new ObjectNotFoundException(DiscountCard.class, id));
        discountCardRepository.delete(discountCard.getId());
    }
}

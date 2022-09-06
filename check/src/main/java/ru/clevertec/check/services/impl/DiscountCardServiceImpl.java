package ru.clevertec.check.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.services.DiscountCardService;
import ru.clevertec.check.util.exception.ObjectNotFoundException;
import ru.clevertec.check.util.mapperMapstruct.DiscountCardMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardMapper mapper;
    private final DiscountCardRepository discountCardRepository;

    @Override
    public DiscountCard findById(long id) throws ObjectNotFoundException {
        return discountCardRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(DiscountCard.class, id));
    }

    @Override
    public List<DiscountCard> allListDiscountCard(int offset, Integer limit) {
        return discountCardRepository.findAll(PageRequest.of(offset, limit)).toList();
    }

    @Override
    public DiscountCard saveCard(DiscountCardDtoForSave card) {
        return discountCardRepository.save(mapper.dtoToEntity(card));
    }

    @Override
    public void deleteCard(long id) {
        discountCardRepository.deleteById(id);
    }

    @Override
    public DiscountCard updateDiscountCard(DiscountCard card) {
        return discountCardRepository.save(card);
    }
}

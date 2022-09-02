package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.DiscountCardRepository;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.mapperMapstruct.DiscountCardMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardMapper mapper;
    private final DiscountCardRepository discountCardRepository;

    @Override
    public Optional<DiscountCard> findById(long id) {
        return discountCardRepository.findById(id);
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
    public DiscountCard updateDiscountCard(DiscountCardDtoForSave card) {
        return discountCardRepository.save(mapper.dtoToEntity(card));
    }
}

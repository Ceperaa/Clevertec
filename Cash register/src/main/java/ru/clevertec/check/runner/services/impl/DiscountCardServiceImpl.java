package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.DiscountCardRepository;
import ru.clevertec.check.runner.services.DiscountCardService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;
    private final ModelMapper modelMapper;

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
        return discountCardRepository.save(modelMapper.map(card, DiscountCard.class));
    }

    @Override
    public void deleteCard(long id) {
        discountCardRepository.deleteById(id);
    }

    @Override
    public DiscountCard updateDiscountCard(DiscountCardDtoForSave card) {
        return discountCardRepository.save(modelMapper.map(card, DiscountCard.class));
    }
}

package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.DiscountCardRepository;
import ru.clevertec.check.runner.util.mapperMapstruct.DiscountCardMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

class DiscountCardServiceImplTest {

    private final DiscountCardServiceImpl cardServices;
    @Mock
    private DiscountCardRepository repository;

    @Mock
    private DiscountCardMapper modelMapper;

    private DiscountCard discountCard;

    DiscountCardServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        this.cardServices = new DiscountCardServiceImpl(modelMapper, repository);
    }

    @BeforeEach
    void setUp() {
        discountCard = new DiscountCard();
        discountCard.setId(1);
        discountCard.setDiscount(10);
    }

    @Test
    @Disabled
    void findById() {
        given(repository.findById(1L)).willReturn(Optional.ofNullable(discountCard));
        cardServices.findById(1L);
        Assertions.assertEquals(cardServices.findById(1L), discountCard);
    }

    @Test
    @Disabled
    void allListDiscountCard() {
        List<DiscountCard> list = List.of(discountCard);
        given(repository.findAll()).willReturn(list);
        Assertions.assertEquals(cardServices.allListDiscountCard(10,10), list);
         }

    @Test
    @Disabled
    void saveCard() {
        DiscountCardDtoForSave discountCardDtoForSave = new DiscountCardDtoForSave(10);
        given(repository.save(discountCard)).willReturn(discountCard);
        cardServices.saveCard(discountCardDtoForSave);
        Assertions.assertEquals(cardServices.saveCard(discountCardDtoForSave), discountCard);
    }

}
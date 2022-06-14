package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.runner.services.DiscountCardServices;

import java.util.List;

import static org.mockito.BDDMockito.given;

class DiscountCardServicesImplTest {

    private final DiscountCardServices cardServices;
    @Mock
    private DiscountCardRepositoryImpl repository;

    private DiscountCard discountCard;

    DiscountCardServicesImplTest() {
        MockitoAnnotations.initMocks(this);
        this.cardServices = new DiscountCardServicesImpl(repository);
    }

    @BeforeEach
    void setUp() {
        discountCard = new DiscountCard();
        discountCard.setId(1);
        discountCard.setDiscount(10);
    }

    @Test
    void findById() {
        given(repository.findById(1L)).willReturn(discountCard);
        cardServices.findById(1L);
        Assertions.assertEquals(cardServices.findById(1L), discountCard);
    }

    @Test
    void allListDiscountCard() throws Exception {
        List<DiscountCard> list = List.of(discountCard);
        given(repository.findAll()).willReturn(list);
        Assertions.assertEquals(cardServices.allListDiscountCard(), list);
    }

    @Test
    void saveCard() throws Exception {
        given(repository.add(discountCard)).willReturn(discountCard);
        cardServices.saveCard(discountCard);
        Assertions.assertEquals(cardServices.saveCard(discountCard), discountCard);
    }

}
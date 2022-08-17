package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.impl.jdbc.DiscountCardRepository;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

class DiscountCardServiceImplTest {

    private final DiscountCardServiceImpl cardServices;
    @Mock
    private DiscountCardRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private DiscountCard discountCard;

    DiscountCardServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        this.cardServices = new DiscountCardServiceImpl(repository, modelMapper);
    }

    @BeforeEach
    void setUp() {
        discountCard = new DiscountCard();
        discountCard.setId(1);
        discountCard.setDiscount(10);
    }

    @Test
    void findById() throws SQLException, ObjectNotFoundException {
        given(repository.findById(1L)).willReturn(Optional.ofNullable(discountCard));
        cardServices.findById(1L);
        Assertions.assertEquals(cardServices.findById(1L), discountCard);
    }

    @Test
    void allListDiscountCard() throws Exception {
        List<DiscountCard> list = List.of(discountCard);
        given(repository.findAll(1,1)).willReturn(list);
        Assertions.assertEquals(cardServices.allList(10,10), list);
    }

    @Test
    void saveCard() throws Exception {
        DiscountCardDtoForSave discountCardDtoForSave = new DiscountCardDtoForSave(10);
        given(repository.add(discountCard)).willReturn(discountCard);
        cardServices.create(discountCardDtoForSave);
        Assertions.assertEquals(cardServices.create(discountCardDtoForSave), discountCard);
    }

}
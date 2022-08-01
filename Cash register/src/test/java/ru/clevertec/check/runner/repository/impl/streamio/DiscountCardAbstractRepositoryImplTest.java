package ru.clevertec.check.runner.repository.impl.streamio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.streamIO.impl.DiscountCardIO;

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;

class DiscountCardAbstractRepositoryImplTest {

    private final DiscountCardRepositoryImpl discountCardRepository;

    @Mock
    private Map<Long, DiscountCard> discountCardMap;
    @Mock
    private DiscountCardIO cardIO;

    private DiscountCard discountCard;

    DiscountCardAbstractRepositoryImplTest() {
        MockitoAnnotations.initMocks(this);
        this.discountCardRepository = new DiscountCardRepositoryImpl(discountCardMap,cardIO);
    }

    @BeforeEach
    void setUp() {
        discountCard = new DiscountCard();
        discountCard.setId(1);
    }

    @Test
    void findAll() throws Exception {
        List<DiscountCard> list = List.of(discountCard);
        given(cardIO.importServiceFile()).willReturn((List)list);
        Assertions.assertEquals(discountCardRepository.findAll(1,1), list);
    }

    @Test
    void add() {
        given(discountCardMap.get(1L)).willReturn(discountCard);
        Assertions.assertEquals(discountCardRepository.findById(1L), discountCard);
    }

}
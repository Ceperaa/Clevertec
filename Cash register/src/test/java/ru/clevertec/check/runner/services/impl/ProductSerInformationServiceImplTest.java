package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.runner.model.entity.ProductInformation;
import ru.clevertec.check.runner.repository.ProductInformationRepository;
import ru.clevertec.check.runner.util.mapperMapstruct.ProductInformationMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductSerInformationServiceImplTest {

    @Mock
    private ProductInformationRepository productInformationRepository;
    @Mock
    private final ProductInformationServiceImpl productInformationService;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private DiscountCardServiceImpl discountCardService;
    @Mock
    ProductInformationMapper modelMapper;

    ProductSerInformationServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        this.productInformationService = new ProductInformationServiceImpl(
                productInformationRepository
                , modelMapper
                , discountCardService
                , productService
        );
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @Disabled
    void discountСalculation() {
        List<ProductInformation> list = List.of(ProductInformation.builder().build());
        double result = productInformationService.discountСalculation(list, 1, 1L);
        assertEquals(1.00, result);
    }

    @Test
    @Disabled
    void addDescriptionInCheck() {
    }

    @Test
    @Disabled
    void findById() {
    }

    @Test
    @Disabled
    void allListProductInformation() {
    }

    @Test
    @Disabled
    void saveProductInformation() {
    }

    @Test
    @Disabled
    void deleteProductInformation() {
    }
}
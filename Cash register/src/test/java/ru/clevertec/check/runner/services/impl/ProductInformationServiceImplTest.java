package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductInformationServiceImplTest {

    @Mock
    private RepositoryEntity<ProductInformation> productInformationRepository;
    @Mock
    private final ProductInformationServiceImpl productInformationService;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private DiscountCardServiceImpl discountCardService;

    ProductInformationServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        ModelMapper modelMapper = new ModelMapper();
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
    void discountСalculation() throws SQLException, ObjectNotFoundException {
        List<ProductInformation> list = List.of(ProductInformation.builder().build());
        double result = productInformationService.discountСalculation(list, 1, 1L);
        assertEquals(1.00, result);
    }

    @Test
    void addDescriptionInCheck() {
    }

    @Test
    void findById() {
    }

    @Test
    void allListProductInformation() {
    }

    @Test
    void saveProductInformation() {
    }

    @Test
    void deleteProductInformation() {
    }
}
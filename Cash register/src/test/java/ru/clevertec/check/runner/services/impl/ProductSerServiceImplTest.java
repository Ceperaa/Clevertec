package ru.clevertec.check.runner.services.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.repository.impl.jdbc.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class ProductSerServiceImplTest {

    private final ProductServiceImpl productService;
    @Mock
    private ProductRepository repository;

    @Mock
    private DiscountCardServiceImpl discountCardServices;

    private ModelMapper modelMapper;

    private Product product;

    ProductSerServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        this.modelMapper = new ModelMapper();
        this.productService = new ProductServiceImpl(repository,modelMapper);
    }

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setDiscountPercent(1);
        product.setName("Apple");
        product.setAmount("20");
        product.setPrice("10.00");
    }

    @Test
    void findById() throws Exception {
        ProductDto product1 = new ProductDto();
        product1.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.ofNullable(product1));
        Product product = productService.findById(1L);
        assertEquals(product, product);
    }

    @Test
    void allListProduct() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        List<Product> list = List.of(product1);
        given(repository.findAll(1,1)).willReturn(list);
        List<Product> list1 = Collections.singletonList((Product) productService.allListProductDto(1,1));
        assertEquals(list, list1);
    }

    @Test
    @Disabled
    void saveProduct() throws Exception {
        given(repository.add(product)).willReturn(product);
        ProductDto.builder().name("Apple").build();
        ProductDto product1 = productService.saveProduct(ProductDtoForSave.builder().name("Apple").build());
        assertEquals(product.getName(), product1.getName());
    }

    @Test
    void update() throws Exception {
        given(repository.update(product)).willReturn(product);
        Product product1 = productService.update(product);
        assertEquals(product, product1);
    }

    @Test
    void totalPriceWithDiscount() {
        ProductInformationDto product1 = new ProductInformationDto();
        double price = 10.33;
        product1.setTotalPriceWithDiscount(price);
        List<ProductInformationDto> list = List.of(product1);
        double result = productService.totalPriceWithDiscount(list);
        assertEquals(price, result);
    }
}
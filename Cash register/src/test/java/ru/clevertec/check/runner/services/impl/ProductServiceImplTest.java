package ru.clevertec.check.runner.services.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForCreate;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.impl.streamio.ProductRepositoryImpl;
import ru.clevertec.check.runner.services.ProductService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class ProductServiceImplTest {

    private final ProductService productService;
    @Mock
    private ProductRepositoryImpl repository;

    @Mock
    private DiscountCardServiceImpl discountCardServices;

    private ModelMapper modelMapper;

    private Product product;

    ProductServiceImplTest() {
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
        product.setPrice(Double.parseDouble("10.00"));
    }

    @Test
    void findById() throws Exception {
        Product product1 = new Product();
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
        given(repository.findAll()).willReturn(list);
        List<Product> list1 = productService.allListProduct();
        assertEquals(list, list1);
    }

    @Test
    @Disabled
    void saveProduct() throws Exception {
        given(repository.add(product)).willReturn(product);
        ProductDtoForCreate.builder().name("Apple").build();
        ProductDto product1 = productService.saveProduct(ProductDtoForCreate.builder().name("Apple").build());
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
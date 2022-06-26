package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.runner.services.ProductServices;
import ru.clevertec.check.runner.streamIO.impl.ProductIO;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

import static org.mockito.BDDMockito.given;

class ProductServicesImplTest {

    private final ProductServices productServices;
    @Mock
    private ProductRepositoryImpl repository;
    @Mock
    private ProductIO productIO;
    @Mock
    private DiscountCardServicesImpl discountCardServices;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private RepositoryEntity<ProductInformation> informationRepositoryEntity;

    private Product product;

    ProductServicesImplTest() {
        MockitoAnnotations.initMocks(this);
        this.productServices = new ProductServicesImpl(repository, productIO, discountCardServices, modelMapper, informationRepositoryEntity);
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
    void findById() throws ObjectNotFoundException {
        Product product1 = new Product();
        product1.setId(1L);
        given(repository.findById(1L)).willReturn(product1);
        Product product = productServices.findById(1);
        Assertions.assertEquals(product, product);
    }

    @Test
    void allListProduct() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        List<Product> list = List.of(product1);
        given(repository.findAll()).willReturn(list);
        List<Product> list1 = productServices.allListProduct();
        Assertions.assertEquals(list, list1);
    }

    @Test
    void saveProduct() throws Exception {
        given(repository.add(product)).willReturn(product);
        Product product1 = productServices.saveProduct(product);
        Assertions.assertEquals(product, product1);
    }

    @Test
    void update() throws Exception {
        given(repository.update(product)).willReturn(product);
        Product product1 = productServices.update(product);
        Assertions.assertEquals(product, product1);
    }

    @Test
    void totalPriceWithDiscount() {
        ProductDto product1 = new ProductDto();
        double price = 10.33;
        product1.setTotalPriceWithDiscount(price);
        List<ProductDto> list = List.of(product1);
        double result = productServices.totalPriceWithDiscount(list);
        Assertions.assertEquals(price, result);
    }

    @Test
    void discountСalculation() {
        List<Product> list = List.of(product);
        double result = productServices.discountСalculation(list, 1, 1L);
        Assertions.assertEquals(1.00, result);
    }
}
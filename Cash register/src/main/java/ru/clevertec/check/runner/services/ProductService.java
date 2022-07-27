package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForCreate;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    Product findById(Long id) throws SQLException, ObjectNotFoundException;

    List<Product> allListProduct() throws Exception;

    ProductDto saveProduct(ProductDtoForCreate product) throws Exception;

    Product update(Product product);

    double totalPriceWithDiscount(List<ProductInformationDto> productList);

}

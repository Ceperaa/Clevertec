package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Map;

public interface ProductServices {

    Product findById(long id) throws ObjectNotFoundException;

    List<Product> allListProduct() throws Exception;

    Product saveProduct(Product product) throws Exception;

    Product update(Product product);

    void exportAllFile() throws Exception;

    void deleteProduct(long id) throws Exception;

    ProductDto addDescriptionInCheck(Map.Entry<Long, Integer> integerMap, Product product);

    double totalPriceWithDiscount(List<ProductDto> productList);

    double discountСalculation(List<Product> productList, double total, Long idCard);


}

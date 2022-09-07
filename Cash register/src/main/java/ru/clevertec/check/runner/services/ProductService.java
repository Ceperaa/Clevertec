package ru.clevertec.check.runner.services;


import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

public interface ProductService {

    Product findById(Long id) throws ObjectNotFoundException;

    Product update(Product product) throws ObjectNotFoundException;

    double totalPriceWithDiscount(List<ProductInformationDto> productList);

}

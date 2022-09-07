package ru.clevertec.check.services;

import ru.clevertec.check.model.dto.ProductInformationDto;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.util.exception.ObjectNotFoundException;

import java.util.List;

public interface ProductService {

    Product findById(long id) throws ObjectNotFoundException;

    void update(Product product) throws ObjectNotFoundException;

    double totalPriceWithDiscount(List<ProductInformationDto> productList);

}

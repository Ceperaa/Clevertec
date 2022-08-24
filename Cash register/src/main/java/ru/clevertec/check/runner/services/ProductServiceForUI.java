package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

public interface ProductServiceForUI {

    ProductDtoForSave findByProductDtoId(Long id) throws ObjectNotFoundException;

    List<ProductDtoForSave> allListProductDto(int offset, int limit);

    ProductDto saveProduct(ProductDtoForSave product);

    void deleteProduct(long id) throws ObjectNotFoundException;

    ProductDtoForSave updateDto(ProductDtoForSave product) throws ObjectNotFoundException;
}

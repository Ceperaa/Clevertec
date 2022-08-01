package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

public interface ProductServiceForUI {

    ProductDto findByProductDtoId(Long id) throws ObjectNotFoundException;

    List<ProductDto> allListProductDto(int offset,int limit);

    ProductDto saveProduct(ProductDtoForSave product);

    void deleteProduct(long id) throws ObjectNotFoundException;

    ProductDtoForSave updateDto(ProductDto product) throws ObjectNotFoundException;
}

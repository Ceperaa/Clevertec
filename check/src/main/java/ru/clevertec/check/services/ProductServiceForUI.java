package ru.clevertec.check.services;

import ru.clevertec.check.model.dto.MaxOfSaleProductDto;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.ProductDtoForSave;
import ru.clevertec.check.util.exception.ObjectNotFoundException;

import java.util.List;

public interface ProductServiceForUI {

    ProductDto findProductDtoById(Long id) throws ObjectNotFoundException;

    List<ProductDto> allListProductDto(int offset, int limit);

    MaxOfSaleProductDto findProductMostSales() throws ObjectNotFoundException;

    ProductDto saveProduct(ProductDtoForSave product);

    void deleteProduct(long id) throws ObjectNotFoundException;

    ProductDto updateDto(ProductDto product) throws ObjectNotFoundException;
}

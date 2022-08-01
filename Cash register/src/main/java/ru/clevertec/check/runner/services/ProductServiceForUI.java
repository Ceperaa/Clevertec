package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductServiceForUI {

    ProductDto findByProductDtoId(Long id) throws SQLException, ObjectNotFoundException;

    List<ProductDto> allListProductDto(int offset,int limit) throws IOException, SQLException;

    ProductDto saveProduct(ProductDtoForSave product) throws Exception;

    void deleteProduct(long id) throws SQLException, ObjectNotFoundException, IOException;

    ProductDtoForSave updateDto(ProductDto product) throws IOException, SQLException;
}

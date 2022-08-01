package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductInformationService {

    ProductInformation findById(Long id) throws SQLException, ObjectNotFoundException;

    ProductInformation saveProductInformation(ProductInformation productInformation) throws IOException, SQLException;

    void deleteProductInformation(long id) throws IOException, SQLException;

    double discountСalculation(List<ProductInformation> productList, double total, Long idCard)
            throws SQLException, ObjectNotFoundException;

    ProductInformation addDescriptionInCheck(
            Map.Entry<Long, Integer> integerMap
            , ProductInformation productInformation
            , List<ProductInformationDto> productCreatDtoList
    );

}
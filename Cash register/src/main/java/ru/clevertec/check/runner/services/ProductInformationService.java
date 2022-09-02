package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.ProductInformation;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductInformationService {

    ProductInformation saveProductInformation(ProductInformation productInformation) throws IOException, SQLException;

    double discountСalculation(List<ProductInformation> productList, double total, Long idCard)
            throws SQLException, ObjectNotFoundException;

    ProductInformation addDescriptionInCheck(
            ProductInformation productInformation,
            List<ProductInformationDto> productInformationDtoList
    ) throws ObjectNotFoundException;

}

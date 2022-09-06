package ru.clevertec.check.services;

import ru.clevertec.check.model.dto.ProductInformationDto;
import ru.clevertec.check.model.entity.ProductInformation;
import ru.clevertec.check.util.exception.ObjectNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ProductInformationService {

    double discountСalculation(List<ProductInformation> productList, double total, Long idCard)
            throws SQLException, ObjectNotFoundException;

    ProductInformation addDescriptionInCheck(
            ProductInformation productInformation,
            List<ProductInformationDto> productInformationDtoList
    ) throws ObjectNotFoundException;

}

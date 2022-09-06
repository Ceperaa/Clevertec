package ru.clevertec.check.util.mapperMapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.model.dto.ProductInformationDto;
import ru.clevertec.check.model.entity.ProductInformation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInformationMapper {

    ProductInformationDto dtoToEntity(ProductInformation productInformation);

}

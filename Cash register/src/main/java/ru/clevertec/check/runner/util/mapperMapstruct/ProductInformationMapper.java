package ru.clevertec.check.runner.util.mapperMapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.ProductInformation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInformationMapper {

    ProductInformationDto dtoToEntity(ProductInformation productInformation);

    ProductInformation entityToDto(ProductInformationDto productInformation);
}

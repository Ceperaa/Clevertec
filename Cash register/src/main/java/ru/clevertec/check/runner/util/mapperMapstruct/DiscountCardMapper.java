package ru.clevertec.check.runner.util.mapperMapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiscountCardMapper {

    DiscountCardDtoForSave entityToDto(DiscountCard source);

    DiscountCard dtoToEntity(DiscountCardDtoForSave destination);
}

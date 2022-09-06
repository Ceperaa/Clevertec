package ru.clevertec.check.util.mapperMapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.model.dto.CheckDto;
import ru.clevertec.check.model.entity.Check;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CheckMapper {

    CheckDto entityToDto(Check check);

}

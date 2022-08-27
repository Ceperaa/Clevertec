package ru.clevertec.check.runner.util.mapperMapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.runner.model.dto.CheckDto;
import ru.clevertec.check.runner.model.entity.Check;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CheckMapper {

    CheckDto entityToDto(Check check);

}

package ru.clevertec.check.util.mapperMapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.ProductDtoForSave;
import ru.clevertec.check.model.entity.Product;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto entityToDto(Product source);

    List<ProductDto> listEntityToListDto(List<Product> productList);

    Product dtoToEntity(ProductDto destination);

    Product dtoToEntityForSave(ProductDtoForSave destination);
}

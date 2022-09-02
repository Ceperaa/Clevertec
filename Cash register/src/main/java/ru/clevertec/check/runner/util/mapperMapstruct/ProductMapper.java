package ru.clevertec.check.runner.util.mapperMapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.model.entity.Product;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto entityToDto(Product source);

    List<ProductDtoForSave> listEntityToListDto(List<Product> productList);

    ProductDtoForSave entityToDtoForSave(Product source);

    Product dtoToEntity(ProductDtoForSave destination);
}

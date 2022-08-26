package ru.clevertec.check.runner.util.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.model.entity.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SimpleSourceDestinationMapper {

    DiscountCardDtoForSave sourceToDestination(DiscountCard source);

    DiscountCard destinationToSource(DiscountCardDtoForSave destination);

    ProductDto sourceToDestination(Product source);

    Product destinationToSource(ProductDto destination);

    ProductDtoForSave sourceToProductDtoForSave(Product source);

    Product destinationToSource(ProductDtoForSave destination);
}

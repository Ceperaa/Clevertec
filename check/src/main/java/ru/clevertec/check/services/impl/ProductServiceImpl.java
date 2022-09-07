package ru.clevertec.check.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.model.dto.MaxOfSaleProductDto;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.ProductDtoForSave;
import ru.clevertec.check.model.dto.ProductInformationDto;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.services.ProductService;
import ru.clevertec.check.services.ProductServiceForUI;
import ru.clevertec.check.util.exception.ObjectNotFoundException;
import ru.clevertec.check.util.mapperMapstruct.ProductMapper;
import ru.clevertec.check.util.validation.DoubleFormatting;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService, ProductServiceForUI {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDto findProductDtoById(Long id) throws ObjectNotFoundException {
        return mapper.entityToDto(findById(id));
    }

    @Override
    public ProductDto saveProduct(ProductDtoForSave product) {
        return mapper
                .entityToDto(
                        productRepository.save(mapper.dtoToEntityForSave(product)));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto updateDto(ProductDto product) {
        return mapper
                .entityToDto(
                        productRepository.save(mapper.dtoToEntity(product)));
    }

    @Override
    public Product findById(long id) throws ObjectNotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class, id));
    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> allListProductDto(int offset, int limit) {
        return mapper
                .listEntityToListDto(
                        productRepository.findAll(PageRequest.of(offset, limit)).toList());
    }

    public MaxOfSaleProductDto findProductMostSales() {
        return productRepository
                .findByProduct();
    }

    public double totalPriceWithDiscount(List<ProductInformationDto> productList) {
        return DoubleFormatting.formatting(productList.stream()
                .map(ProductInformationDto::getTotalPriceWithDiscount)
                .mapToDouble(Double::doubleValue).sum());
    }
}

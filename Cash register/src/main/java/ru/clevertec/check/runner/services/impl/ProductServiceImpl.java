package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.MaxOfSaleProductDto;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.repository.ProductRepository;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.mapperMapstruct.ProductMapper;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService, ProductServiceForUI {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDtoForSave findByProductDtoId(Long id) throws ObjectNotFoundException {
        return mapper.entityToDtoForSave(findById(id));
    }

    @Override
    public ProductDto saveProduct(ProductDtoForSave product) {
        return mapper
                .entityToDto(
                        productRepository.save(mapper.dtoToEntity(product)));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDtoForSave updateDto(ProductDtoForSave product) {
        return mapper
                .entityToDtoForSave(
                        productRepository.save(mapper.dtoToEntity(product)));
    }

    @Override
    public Product findById(long id) throws ObjectNotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class, id));
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }


    @Override
    public List<ProductDtoForSave> allListProductDto(int offset, int limit) {
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

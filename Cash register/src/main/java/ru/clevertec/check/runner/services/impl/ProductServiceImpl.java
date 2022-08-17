package ru.clevertec.check.runner.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.EntityServiceCrud;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("productServiceImpl")
@AllArgsConstructor
public class ProductServiceImpl implements ProductService, EntityServiceCrud<ProductDto> {

    private final RepositoryEntity<Product> productRepository;
    private final ModelMapper modelMapper;

    public Product findByProductId(Long id) throws ObjectNotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class, id));
    }

    public ProductDto findById(Long id) throws ObjectNotFoundException {
        return modelMapper.map(findByProductId(id), ProductDto.class);
    }

    @Override
    public Product update(Product product) {
       return productRepository.update(product);
    }

    public List<Object> allListProduct() {
        return Collections.singletonList(productRepository.findAll(0, 100));
    }

    public List<ProductDto> allList(int offset, int limit) {
        return productRepository.findAll(limit, offset)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                 .collect(Collectors.toList());
    }

    public ProductDto create(Object product) {
        return modelMapper
                .map(productRepository.add(
                        modelMapper.map(product, Product.class)), ProductDto.class);
    }

    public ProductDto update(Object product) {
            return modelMapper.map(productRepository.update(modelMapper.map(product, Product.class)), ProductDto.class);
    }

    public void delete(long id) throws ObjectNotFoundException {
            productRepository.delete(id);
    }

    public double totalPriceWithDiscount(List<ProductInformationDto> productList) {
        return DoubleFormatting.formatting(productList.stream()
                .map(ProductInformationDto::getTotalPriceWithDiscount)
                .mapToDouble(Double::doubleValue).sum());
    }
}

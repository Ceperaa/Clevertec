package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, ProductServiceForUI {

    private final RepositoryEntity<Product> productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(
            RepositoryEntity<Product> productRepository
            , ModelMapper modelMapper
    ) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Product findById(Long id) throws ObjectNotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class, id));
    }

    public ProductDto findByProductDtoId(Long id) throws ObjectNotFoundException {
        return modelMapper.map(findById(id), ProductDto.class);
    }

    public List<Product> allListProduct() {
        return productRepository.findAll(0,100);
    }

    public List<ProductDto> allListProductDto(int offset, int limit) {
        return productRepository.findAll(limit, offset)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                 .collect(Collectors.toList());
    }

    public ProductDto saveProduct(ProductDtoForSave product) {
        return modelMapper
                .map(productRepository.add(
                        modelMapper.map(product, Product.class)), ProductDto.class);
    }

    public Product update(Product product) {
            return productRepository.update(product);
    }

    public ProductDtoForSave updateDto(ProductDto product) {
            return modelMapper.map(productRepository.update(modelMapper.map(product, Product.class)), ProductDtoForSave.class);
    }

    public void deleteProduct(long id) throws ObjectNotFoundException {
            productRepository.delete(id);
    }

    public double totalPriceWithDiscount(List<ProductInformationDto> productList) {
        return DoubleFormatting.formatting(productList.stream()
                .map(ProductInformationDto::getTotalPriceWithDiscount)
                .mapToDouble(Double::doubleValue).sum());
    }
}

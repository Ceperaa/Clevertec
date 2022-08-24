package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService, ProductServiceForUI {

    private final RepositoryEntity<Product> productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDtoForSave findByProductDtoId(Long id) throws ObjectNotFoundException {
        return modelMapper.map(findById(id), ProductDtoForSave.class);
    }


    @Override
    public ProductDto saveProduct(ProductDtoForSave product) {
        return modelMapper
                .map(productRepository.add(
                        modelMapper.map(product, Product.class)), ProductDto.class);
    }

    @Override
    public void deleteProduct(long id) throws ObjectNotFoundException {
        productRepository.delete(id);
    }

    @Override
    public ProductDtoForSave updateDto(ProductDtoForSave product) {
        return modelMapper.map(productRepository.update(modelMapper.map(product, Product.class)), ProductDtoForSave.class);
    }

    @Override
    public Product findById(Long id) throws ObjectNotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class, id));
    }

    @Override
    public Product update(Product product) {
        return productRepository.update(product);
    }


    @Override
    public List<ProductDtoForSave> allListProductDto(int offset, int limit) {
        return productRepository.findAll(limit, offset)
                .stream()
                .map(product -> modelMapper.map(product, ProductDtoForSave.class))
                .collect(Collectors.toList());
    }


    public double totalPriceWithDiscount(List<ProductInformationDto> productList) {
        return DoubleFormatting.formatting(productList.stream()
                .map(ProductInformationDto::getTotalPriceWithDiscount)
                .mapToDouble(Double::doubleValue).sum());
    }
}

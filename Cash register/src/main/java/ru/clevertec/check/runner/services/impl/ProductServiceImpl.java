package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.repository.ProductRepository;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.mapstruct.SimpleSourceDestinationMapper;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.List;
import java.util.stream.Collectors;

@Service("productServiceImpl")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService, ProductServiceForUI {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final SimpleSourceDestinationMapper mapper;

    @Override
    public ProductDtoForSave findByProductDtoId(Long id) throws ObjectNotFoundException {
       // return modelMapper.map(findByProductDtoId(id), ProductDtoForSave.class);
        return mapper.sourceToProductDtoForSave(findById(id));
    }


    @Override
    public ProductDto saveProduct(ProductDtoForSave product) {
        return modelMapper
                .map(productRepository.save(
                        modelMapper.map(product, Product.class)), ProductDto.class);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDtoForSave updateDto(ProductDtoForSave product) {
        return modelMapper.map(productRepository.save(modelMapper.map(product, Product.class)), ProductDtoForSave.class);

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
        return productRepository.findAll(PageRequest.of(offset, limit))
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

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
import ru.clevertec.check.runner.util.exception.Pagination;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.io.IOException;
import java.sql.SQLException;
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

    public Product findById(Long id) throws SQLException, ObjectNotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product id:" + id + " not found"));
    }

    public ProductDto findByProductDtoId(Long id) throws SQLException, ObjectNotFoundException {
        return modelMapper.map(findById(id), ProductDto.class);
    }

    public List<Product> allListProduct() throws Exception {
        return productRepository.findAll();
    }

    public List<ProductDto> allListProductDto(int offset, int limit) throws IOException, SQLException {
        return Pagination.getPage(productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList()), offset, limit);
//        return productRepository.findAll()
//                .stream()
//                .map(product -> modelMapper.map(product, ProductDto.class))
//                .collect(Collectors.toList());
    }
    public ProductDto saveProduct(ProductDtoForSave product) throws IOException, SQLException {
        return modelMapper
                .map(productRepository.add(
                        modelMapper.map(product, Product.class)), ProductDto.class);
    }

    public Product update(Product product) {
        try {
            return productRepository.update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductDtoForSave updateDto(ProductDto product) throws IOException, SQLException {
        return modelMapper.map(productRepository.update(modelMapper.map(product, Product.class)), ProductDtoForSave.class);
    }

    public void deleteProduct(long id) throws SQLException, ObjectNotFoundException, IOException {
        productRepository.delete(findById(id).getId());
    }

    public double totalPriceWithDiscount(List<ProductInformationDto> productList) {
        return DoubleFormatting.formatting(productList.stream()
                .map(ProductInformationDto::getTotalPriceWithDiscount)
                .mapToDouble(Double::doubleValue).sum());
    }
}

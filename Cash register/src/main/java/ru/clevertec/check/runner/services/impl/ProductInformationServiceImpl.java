package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.services.ProductInformationService;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ProductInformationServiceImpl implements ProductInformationService {

    private final RepositoryEntity<ProductInformation> productInformationRepository;
    private final ModelMapper modelMapper;
    private final DiscountCardService discountCardService;
    private final ProductService productService;
    private static final int NUMBER_PRODUCT_ON_SALE = 5;
    private static final int PROMOTIONAL_DISCOUNT = 10;

    public ProductInformationServiceImpl(
            RepositoryEntity<ProductInformation> productInformationRepository
            , ModelMapper modelMapper, DiscountCardService discountCardService
            , ProductService productService
    ) {
        this.productInformationRepository = productInformationRepository;
        this.modelMapper = modelMapper;
        this.discountCardService = discountCardService;
        this.productService = productService;
    }

    @Override
    public double discountСalculation(
            List<ProductInformation> productList, double total, Long idCard)
            throws SQLException, ObjectNotFoundException {
        if (idCard != 0) {
            total = subtractPercentage(discountCardService.findById(idCard).get().getDiscount(), total);
        }
        if (productList
                .stream()
                .filter(product -> product.getDiscountPercent() != 0)
                .count() >= NUMBER_PRODUCT_ON_SALE
        ) {
            total = subtractPercentage(PROMOTIONAL_DISCOUNT, total);
        }
        return DoubleFormatting.formatting(total);
    }

    public ProductInformation addDescriptionInCheck(
            Map.Entry<Long, Integer> integerMap
            , ProductInformation productInformation
            , List<ProductInformationDto> productInformationDtoList
    ) throws ObjectNotFoundException {
        int amountProduct = integerMap.getValue();
        Product product = productInformation.getProduct();
        productInformation.setPriceWithDiscount(subtractPercentage(product.getDiscountPercent()
                , product.getPrice()));
        if (Integer.parseInt(product.getAmount()) >= amountProduct) {
            mapDescription(productInformation, product, amountProduct);
            int result = Integer.parseInt(product.getAmount()) - amountProduct;
            product.setAmount(String.valueOf(result));
        } else {
            mapDescription(productInformation, product, Integer.parseInt(product.getAmount()));
            product.setAmount("0");
        }
        productInformation.setAmount(amountProduct);
        productService.update(product);
        productInformationDtoList.add(mapProductDto(product, productInformation));
        return productInformation;
    }

    private ProductInformationDto mapProductDto(Product product, ProductInformation productInformation) {
        ProductInformationDto productInformationDto = modelMapper.map(productInformation, ProductInformationDto.class);
        productInformationDto.setPrice(String.valueOf(product.getPrice()));
        //productInformationDto.setAmount(product.getAmount());
        productInformationDto.setName(product.getName());
        return productInformationDto;
    }

    private void mapDescription(ProductInformation productInformation, Product product, int amount) {
        productInformation.setTotalPriceWithDiscount(
                DoubleFormatting.formatting(
                        productInformation.getPriceWithDiscount() * amount)
        );
        productInformation.setTotalPrice(product.getPrice() * amount);
    }

    private double subtractPercentage(int percent, double price) {
       final int TOTAL_PERCENT = 100;
        return DoubleFormatting.formatting(price - (price * percent) / TOTAL_PERCENT);
    }

//    public ProductInformation findById(Long id) throws SQLException, ObjectNotFoundException {
//        return productInformationRepository
//                .findById(id)
//                .orElseThrow(() -> new ObjectNotFoundException(ProductInformation.class,id));
//    }

    public ProductInformation saveProductInformation(ProductInformation productInformation) throws IOException, SQLException {
        return productInformationRepository.add(productInformation);
    }

//    public void deleteProductInformation(long id) throws IOException, SQLException {
//        try {
//
//        }
//        productInformationRepository.delete(id);
//    }
}

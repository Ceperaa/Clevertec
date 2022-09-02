package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.model.entity.ProductInformation;
import ru.clevertec.check.runner.repository.ProductInformationRepository;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.services.ProductInformationService;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.mapperMapstruct.ProductInformationMapper;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInformationServiceImpl implements ProductInformationService {

    private final ProductInformationRepository productInformationRepository;
    private final ProductInformationMapper mapper;
    private final DiscountCardService discountCardService;
    private final ProductService productService;
    private static final int NUMBER_PRODUCT_ON_SALE = 5;
    private static final int PROMOTIONAL_DISCOUNT = 10;

    @Override
    public double discountСalculation(
            List<ProductInformation> productList, double total, Long idCard) {
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
            ProductInformation productInformation,
            List<ProductInformationDto> productInformationDtoList
    ) throws ObjectNotFoundException {
        Product product = productInformation.getProduct();
        productInformation.setPriceWithDiscount(
                subtractPercentage(product.getDiscountPercent(),
                        Double.parseDouble(product.getPrice()))
        );
        saveAmountProduct(product, productInformation);
        productService.update(product);
        productInformationDtoList.add(mapProductDto(product, productInformation));
        return productInformation;
    }

    private void saveAmountProduct(Product product, ProductInformation productInformation) {
        int amountProduct = productInformation.getAmount();
        if (Integer.parseInt(product.getAmount()) >= amountProduct) {
            mapDescription(productInformation, product, amountProduct);
            int result = Integer.parseInt(product.getAmount()) - amountProduct;
            product.setAmount(String.valueOf(result));
        } else {
            mapDescription(productInformation, product, Integer.parseInt(product.getAmount()));
            product.setAmount("0");
        }
    }

    private ProductInformationDto mapProductDto(Product product, ProductInformation productInformation) {
        ProductInformationDto productInformationDto = mapper.dtoToEntity(productInformation);
        productInformationDto.setPrice(String.valueOf(product.getPrice()));
        productInformationDto.setName(product.getName());
        return productInformationDto;
    }

    private void mapDescription(ProductInformation productInformation, Product product, int amount) {
        productInformation.setTotalPriceWithDiscount(
                DoubleFormatting.formatting(
                        productInformation.getPriceWithDiscount() * amount)
        );
        productInformation.setTotalPrice(Double.parseDouble(product.getPrice()) * amount);
    }

    private double subtractPercentage(int percent, double price) {
        final int TOTAL_PERCENT = 100;
        return DoubleFormatting.formatting(price - (price * percent) / TOTAL_PERCENT);
    }

    public ProductInformation saveProductInformation(ProductInformation productInformation) {
        ProductInformation save = productInformationRepository.save(productInformation);
        return save;
    }
}

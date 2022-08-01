package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.Transactional;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.services.ProductInformationService;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey Degtyarev
 */
@Service
public class CheckRunnerServiceImpl implements CheckRunnerService {

    private final ProductService productService;
    private final RepositoryEntity<Check> checkRepository;
    private final ProductInformationService productInformationService;
    private final ModelMapper modelMapper;

    public CheckRunnerServiceImpl(
            ProductService productService
            , RepositoryEntity<Check> checkRepository
            , ProductInformationService productInformationService, ModelMapper modelMapper
    ) {
        this.productService = productService;
        this.checkRepository = checkRepository;
        this.productInformationService = productInformationService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CheckDto createCheck(List<String> itemIdQuantity, Long idCard)
            throws SQLException, ObjectNotFoundException, IOException {

        List<ProductInformationDto> productInformationList = new ArrayList<>();
        List<ProductInformation> productList = new ArrayList<>();
        productListForCheck(splitItemIdQuantity(itemIdQuantity), productList, productInformationList);

        double totalPrice = totalPrice(productInformationList);
        double totalPriceWithDiscount = productInformationService.discountСalculation(productList
                , productService.totalPriceWithDiscount(productInformationList)
                , idCard);
        double discountAmount = totalPrice - totalPriceWithDiscount;

        Check check = new Check();
        check.setProductList(productList);
        check.setTotalPrice(totalPrice);
        check.setTotalPriceWithDiscount(totalPriceWithDiscount);
        check.setDiscountAmount(DoubleFormatting.formatting(discountAmount));
        final int TOTAL_PERCENT = 100;
        if (check.getTotalPrice() != 0) {
            check.setTotalPercent((int) ((discountAmount * TOTAL_PERCENT) / check.getTotalPrice()));
        } else {
            check.setDiscountAmount(0);
        }

        check = saveCheck(check);
        saveProductInformationList(productList, check);
        return mapToCheckDto(check, productInformationList);
    }

    private void saveProductInformationList(List<ProductInformation> productList, Check check) throws IOException, SQLException {
        for (ProductInformation productInformation : productList) {
            productInformation.setCheck(check);
            productInformationService.saveProductInformation(productInformation);
        }
    }

    private void productListForCheck(
            Map<Long, Integer> map
            , List<ProductInformation> productList
            , List<ProductInformationDto> productInformationDtoList
    ) throws SQLException, ObjectNotFoundException {
        for (Map.Entry<Long, Integer> integerEntry : map.entrySet()) {
            Product product = productService.findById(integerEntry.getKey());
            productList.add(productInformationService.addDescriptionInCheck(
                    integerEntry
                    , ProductInformation
                            .builder()
                            .totalPrice(product.getPrice())
                            .discountPercent(product.getDiscountPercent())
                            .product(product)
                            .build()
                    , productInformationDtoList
            ));
        }
    }

    private double totalPrice(List<ProductInformationDto> productList) {
        return productList.stream().map(ProductInformationDto::getTotalPrice).mapToDouble(Double::doubleValue).sum();
    }

    public CheckDto mapToCheckDto(Check check, List<ProductInformationDto> productInformationDtoList) {
        CheckDto checkDto = modelMapper.map(check, CheckDto.class);
        checkDto.setProductList(productInformationDtoList);
        return checkDto;
    }

    private Map<Long, Integer> splitItemIdQuantity(List<String> itemIdQuantity) {
        Map<Long, Integer> integerMap = new HashMap<>();
        for (String s : itemIdQuantity) {
            String[] strings = s.split("-");
            integerMap.put(Long.parseLong(strings[0]), Integer.parseInt(strings[1]));
        }
        return integerMap;
    }

    public Check saveCheck(Check check) throws IOException, SQLException {
        return checkRepository.add(check);
    }
}


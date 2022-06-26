package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.CheckRunnerServices;
import ru.clevertec.check.runner.services.ProductServices;
import ru.clevertec.check.runner.streamIO.impl.CheckIO;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey Degtyarev
 */

@Service
public class CheckRunnerServicesImpl implements CheckRunnerServices {

    private final ProductServices productServices;
    private final RepositoryEntity<Check> checkRepository;
    private final CheckIO checkIO;
    private final ModelMapper modelMapper;

    public CheckRunnerServicesImpl(
            ProductServices productServices
            , RepositoryEntity<Check> checkRepository
            , CheckIO checkIO,
            ModelMapper modelMapper) {
        this.productServices = productServices;
        this.checkRepository = checkRepository;
        this.checkIO = checkIO;
        this.modelMapper = modelMapper;
    }

    public CheckDto creatCheck(List<String> itemIdQuantity, Long idCard) throws Exception {

        Map<Long, Integer> integerMap = splitItemIdQuantity(itemIdQuantity);

        List<ProductDto> productInformationList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        productListFromCheck(integerMap, productList, productInformationList);
        productServices.exportAllFile();

        double totalPrice = totalPrice(productInformationList);
        double totalPriceWithDiscount = productServices.discountСalculation(productList
                , productServices.totalPriceWithDiscount(productInformationList)
                , idCard);
        double discountAmount = totalPrice - totalPriceWithDiscount;

        Check check = new Check();
        check.setProductList(productList);
        check.setTotalPrice(totalPrice);
        check.setTotalPriceWithDiscount(totalPriceWithDiscount);
        check.setDiscountAmount(DoubleFormatting.formatting(discountAmount));

        if (check.getTotalPrice() != 0) {
            check.setTotalPercent((int) ((discountAmount * 100) / check.getTotalPrice()));
        } else {
            check.setDiscountAmount(0);
        }

        check = add(check);
        exportFile(check);

        return mapToCheckDto(check,productInformationList);
    }

    private void productListFromCheck(
            Map<Long, Integer> map
            , List<Product> productList
            , List<ProductDto> productDtoList
    ) throws Exception {
            for (Map.Entry<Long, Integer> integerEntry : map.entrySet()) {
                Product product = productServices.findById(integerEntry.getKey());
                productList.add(product);
                ProductDto productDto = productServices.addDescriptionInCheck(integerEntry, product);
                productDtoList.add(productDto);
            }
    }

    public void exportFile(Check check) throws Exception {
        checkIO.exportFile(List.of(check), false);
    }

    private double totalPrice(List<ProductDto> productList) {
        return productList.stream().map(ProductDto::getTotalPrice).mapToDouble(Double::doubleValue).sum();
    }

    public CheckDto mapToCheckDto(Check check, List<ProductDto> productDtoList) {
        CheckDto checkDto = modelMapper.map(check, CheckDto.class);
        checkDto.setProductList(productDtoList);
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

    public Check add(Check check) throws Exception {
//        List<ProductDto> list = check.getProductList();
//        list.stream()
//                .peek(productInformation -> productInformation.setCheckId(check.getId()))
//                .collect(Collectors.toList());
        return checkRepository.add(check);
    }
}


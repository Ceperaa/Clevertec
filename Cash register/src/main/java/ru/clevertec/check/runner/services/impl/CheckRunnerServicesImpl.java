package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.CheckRepositoryImpl;
import ru.clevertec.check.runner.services.CheckRunnerServices;
import ru.clevertec.check.runner.streamIO.impl.CheckIO;
import ru.clevertec.check.runner.util.validation.DataValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sergey Degtyarev
 */

@Service
public class CheckRunnerServicesImpl implements CheckRunnerServices {

    private final ProductServicesImpl productServices;
    private final RepositoryEntity<Check> checkRepository;
    private final CheckIO checkIO;
    private final ModelMapper modelMapper;

    public CheckRunnerServicesImpl(
            ProductServicesImpl productServices
            , CheckRepositoryImpl checkRepository
            , CheckIO checkIO,
            ModelMapper modelMapper) {
        this.productServices = productServices;
        this.checkRepository = checkRepository;
        this.checkIO = checkIO;
        this.modelMapper = modelMapper;
    }

    public CheckDto creatCheck(String[] itemIdQuantity, Long idCard) throws Exception {


        Map<Long, Integer> integerMap = splitItemIdQuantity(DataValidation.validator(itemIdQuantity));

        Check check = new Check();
        List<ProductDto> productInformationList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        for (Map.Entry<Long, Integer> integerEntry : integerMap.entrySet()) {
            Product product = productServices.findById(integerEntry.getKey());
            productList.add(product);
            productInformationList.add(productServices.addDescriptionInCheck(integerEntry, product));
        }
        productServices.exportAllFile();
        check.setProductList(productInformationList);
        check.setTotalPrice(totalPrice(productInformationList));
        check.setTotalPriceWithDiscount(productServices.discountСalculation(productList, productServices.totalPriceWithDiscount(productInformationList), idCard));

        double discountAmount = check.getTotalPrice() - check.getTotalPriceWithDiscount();
        check.setDiscountAmount(discountAmount);

        if (check.getTotalPrice() != 0) {
            check.setTotalPercent((int) ((discountAmount * 100) / check.getTotalPrice()));
        } else {
            check.setDiscountAmount(0);
        }

        check = add(check);
        exportFile(check);

        return mapToCheckDto(check);
    }

    public void exportFile(Check check) throws Exception {
        checkIO.exportFile(List.of(check), false);
    }

    private int totalPrice(List<ProductDto> productList) {
        return productList.stream().map(ProductDto::getTotalPrice).mapToInt(Double::intValue).sum();
    }

    public CheckDto mapToCheckDto(Check check){
       return modelMapper.map(check,CheckDto.class);
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
//в чек не сетится айди
        List<ProductDto> list = check.getProductList();
        list.stream()
                .peek(productInformation -> productInformation.setCheckId(check.getId()))
                .collect(Collectors.toList());
        return checkRepository.add(check);
    }
}


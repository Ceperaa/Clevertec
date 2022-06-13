package ru.clevertec.check.runner.services.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.CheckRepositoryImpl;
import ru.clevertec.check.runner.services.CheckRunnerServices;
import ru.clevertec.check.runner.streamIO.impl.CheckIO;
import ru.clevertec.check.runner.util.exception.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Sergey Degtyarev
 */

@Service
public class CheckRunnerServicesImpl implements CheckRunnerServices {

    private final ProductServicesImpl productServices;
    private final RepositoryEntity<Check> checkRepository;
    private final ProductInformationService productInformationService;
    private final CheckIO checkIO;

    public CheckRunnerServicesImpl(
            ProductServicesImpl productServices
            , CheckRepositoryImpl checkRepository
            , ProductInformationService productInformationService
            , CheckIO checkIO
    ) {
        this.productServices = productServices;
        this.checkRepository = checkRepository;
        this.productInformationService = productInformationService;
        this.checkIO = checkIO;
    }

    public Check creatCheck(String[] itemIdQuantity, Long idCard) throws Exception {

        Map<Long, Integer> integerMap = splitItemIdQuantity(validation(itemIdQuantity));

        Check check = new Check();
        List<ProductInformation> productInformationList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        integerMap.entrySet().forEach(integerEntry -> {
            Product product = productServices.findById(integerEntry.getKey());
            productList.add(product);
            productInformationList.add(productInformationService.addDescriptionInCheck(integerEntry, product));
        });
        productServices.exportAllFile();
        check.setProductList(productInformationList);
        check.setTotalPrice(totalPrice(productInformationList));
        check.setTotalPriceWithDiscount(productInformationService.discountСalculation(productList, productInformationService.totalPriceWithDiscount(productInformationList), idCard));

        int discountAmount = check.getTotalPrice() - check.getTotalPriceWithDiscount();
        check.setDiscountAmount(discountAmount);

        if (check.getTotalPrice() != 0) {
            check.setTotalPercent((discountAmount * 100) / check.getTotalPrice());
        } else {
            check.setDiscountAmount(0);
        }

        check = add(check);
        exportFile(check);

        return check;
    }

    public void exportFile(Check check) throws Exception {
        checkIO.exportFile(List.of(check), false);
    }

    private int totalPrice(List<ProductInformation> productList) {
        return productList.stream().map(ProductInformation::getTotalPrice).mapToInt(x -> x).sum();
    }

    //протестировать
    private String[] validation(String[] itemIdQuantity) {
        String[] string = new String[itemIdQuantity.length];
        for (String s : itemIdQuantity) {
            Pattern pattern = Pattern.compile("[0-9]*-[0-9]*");
            Matcher matcher = pattern.matcher(s);
            for (String s1 : string) {
                s1 = matcher.group();
            }
        }
        return string;
    }

    private Map<Long, Integer> splitItemIdQuantity(String[] itemIdQuantity) throws ValidationException {
        Map<Long, Integer> integerMap = new HashMap<>();
        try {
            for (String s : itemIdQuantity) {
                String[] strings = s.split("-");
                integerMap.put(Long.parseLong(strings[0]), Integer.parseInt(strings[1]));
            }
        } catch (Exception e) {
            throw new ValidationException("Incorrect input - " + e.getMessage());
        }
        return integerMap;
    }

    public void saveCheck(Check check) throws Exception {
        checkRepository.update(check);
    }

    public Check add(Check check) throws Exception {
//в чек не сетится айди
        List<ProductInformation> list = check.getProductList();
        list.stream()
                .peek(productInformation -> productInformation.setCheckId(check.getId()))
                .collect(Collectors.toList());
        productInformationService.exportFile(list);
        return checkRepository.add(check);
    }
}


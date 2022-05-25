package ru.clevertec.check.runner.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.DiscountCardRepository;
import ru.clevertec.check.runner.repository.ProductRepository;
import ru.clevertec.check.runner.services.CheckRunnerServices;
import ru.clevertec.check.runner.util.exception.ValidationException;

import java.util.*;

/**
 *
 * @author Sergey Degtyarev
 */

@Service
public class CheckRunnerServicesImpl implements CheckRunnerServices {

    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public CheckRunnerServicesImpl(ProductRepository productService, DiscountCardRepository discountCardRepositoryImpl) {
        this.productRepository = productService;
        this.discountCardRepository = discountCardRepositoryImpl;
    }

    public Check creatCheck(String[] itemIdQuantity, Integer idCard) throws Exception {

        Map<Long, Integer> integerMap = splitItemIdQuantity(itemIdQuantity);

        Check check = new Check();
        List<ProductInformation> productInformationList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        integerMap.entrySet().forEach(integerEntry -> {
            Product product = productRepository.get(integerEntry.getKey());
            productList.add(product);
            productInformationList.add(addDescriptionInCheck(integerEntry, product));
        });
        check.setProductList(productInformationList);
        check.setTotalPrice(totalPrice(productInformationList));
        check.setTotalPriceWithDiscount(discountСalculation(productList, totalPriceWithDiscount(productInformationList), idCard));
        int discountAmount = check.getTotalPrice() - check.getTotalPriceWithDiscount();
        check.setDiscountAmount(discountAmount);
        if (check.getTotalPrice()!=0){
            check.setTotalPercent((discountAmount * 100) / check.getTotalPrice());
        }else {
            check.setDiscountAmount(0);
        }
        return check;
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

    private ProductInformation addDescriptionInCheck(Map.Entry<Long, Integer> integerMap, Product product) {
        ProductInformation productInformation =
                new ProductInformation(
                        product.getName()
                        , product.getPrice()
                        , subtractPercentage(product.getDiscountPercent()
                        , product.getPrice()));

        if (product.getAmount() >= integerMap.getValue()) {
            mapDescription(productInformation, product, integerMap.getValue());
            product.setAmount(product.getAmount() - integerMap.getValue());
        } else {
            mapDescription(productInformation, product, product.getAmount());
            product.setAmount(0);
        }
        productRepository.save(product);
        return productInformation;
    }

    private void mapDescription(ProductInformation productInformation, Product product, int amount) {
        productInformation.setQty(amount);
        productInformation.setTotalPriceWithDiscount(productInformation.getPriceWithDiscount() * amount);
        productInformation.setTotalPrice(product.getPrice() * amount);
    }

    private int totalPrice(List<ProductInformation> productList) {
        return productList.stream().map(ProductInformation::getTotalPrice).mapToInt(x -> x).sum();
    }

    private int totalPriceWithDiscount(List<ProductInformation> productList) {
        return productList.stream().map(ProductInformation::getTotalPriceWithDiscount).mapToInt(x -> x).sum();
    }

    private int discountСalculation(List<Product> productList, int total, Integer idCard) {
        if (discountCardRepository.get(idCard)!=null){
            total = subtractPercentage(discountCardRepository.get(idCard).getDiscount(),total);
        }
        if (productList
                .stream()
                .filter(product -> product.getDiscountPercent() != 0)
                .count() >= 5
        ) {
            total = subtractPercentage(10, total);
        }
        return total;
    }

    private int subtractPercentage(int percent, int price) {
        return price - (price * percent) / 100;
    }
}


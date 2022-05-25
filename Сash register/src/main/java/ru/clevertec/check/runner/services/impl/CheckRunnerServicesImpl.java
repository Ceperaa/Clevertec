package ru.clevertec.check.runner.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.DiscountCardRepository;
import ru.clevertec.check.runner.repository.ProductRepository;
import ru.clevertec.check.runner.services.CheckRunnerServices;

import java.util.*;

@Service
public class CheckRunnerServicesImpl implements CheckRunnerServices {

    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public CheckRunnerServicesImpl(ProductRepository productService, DiscountCardRepository discountCardRepositoryImpl) {
        this.productRepository = productService;
        this.discountCardRepository = discountCardRepositoryImpl;
    }

    public Check run(String[] itemIdQuantity1, int idCard) throws Exception {

        Map<Long, Integer> integerMap = new HashMap<>();
        try {
            for (String s : itemIdQuantity1) {
                String[] strings = s.split("-");
                integerMap.put(Long.parseLong(strings[0]), Integer.parseInt(strings[1]));
            }
        } catch (Exception e) {
            throw new Exception("Incorrect input - " + e.getMessage());
        }

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
        check.setTotalPriceWithDiscount(isDiscount(productList, totalPriceWithDiscount(productInformationList), idCard));
        int discountAmount = check.getTotalPrice() - check.getTotalPriceWithDiscount();
        check.setDiscountAmount(discountAmount);
        check.setTotalPercent((discountAmount * 100) / check.getTotalPrice());
        return check;
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

    private int isDiscount(List<Product> productList, int total, int idCard) {
        total = subtractPercentage(Optional.of(discountCardRepository.get(idCard).getDiscount()).orElse(0), total);
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


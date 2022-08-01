package ru.clevertec.check.runner.streamIO.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.ProductInformation;

import java.util.Map;

@Component
public class ProductInformationIO extends StreamIO {

    private final static String LINK_ADDRESS =
            "E:\\Clevertec\\Cash register\\src\\main\\resources\\files\\ProductInformation.CSV";

    public ProductInformationIO() {
        super(LINK_ADDRESS);
    }

    @Override
    protected Object objectAssembly(String text) {
        ProductInformation productInformation = new ProductInformation();
        for (Map.Entry<String, String> entry : super.parseMap(text).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case ("ProductInformation id"):
                    productInformation.setId(Long.parseLong(value));
                    break;
                case ("discountPercent"):
                    productInformation.setDiscountPercent(Integer.parseInt(value));
                    break;
                case ("totalPriceWithDiscount"):
                    productInformation.setPriceWithDiscount(Double.valueOf(value));
                    break;
                case ("totalPrice"):
                    if (value.compareTo("null") != 0) {
                        productInformation.setTotalPrice(Double.parseDouble(value));
                    }
                    break;
            }
        }
        return productInformation;
    }
}
package ru.clevertec.check.runner.streamIO.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.Product;

import java.util.Map;

@Component
public class ProductIO extends StreamIO {

    private final static String LINK_ADDRESS =
            "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\ProductFile.CSV";

    @Autowired
    public ProductIO() {
        super(LINK_ADDRESS);
    }

    protected Product objectAssembly(String text) {
        Product product = new Product();
        for (Map.Entry<String, String> entry : super.parseMap(text).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case ("Product id"):
                    product.setId(Long.parseLong(value));
                    break;
                case ("name"):
                    product.setName(value);
                    break;
                case ("amount"):
                    product.setAmount(value);
                    break;
                case ("price"):
                    if (value.compareTo("null") != 0) {
                        product.setPrice(value);
                    }
                    break;
                case ("discountPercent"):
                    if (value.compareTo("null") != 0) {
                        product.setDiscountPercent(Integer.parseInt(value));
                    }
                    break;
            }
        }
        return product;
        //informationRepository.update(product);
    }
}

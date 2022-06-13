package ru.clevertec.check.runner.streamIO.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.ProductInformation;

@Component
public class ProductInformationIO extends StreamIO {

    private final static String LINK_ADDRESS =
            "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\DescriptionProductFile.CSV";

    @Autowired
    public ProductInformationIO() {
        super(LINK_ADDRESS);
    }

    protected ProductInformation objectAssembly(String text) {
        ProductInformation productInformation = new ProductInformation();
        super.parseMap(text).forEach((key, value) -> {
            switch (key) {
                case ("ProductInformation id"):
                    productInformation.setId(Integer.parseInt(value));
                    break;
                case ("qty"):
                    productInformation.setQty(Integer.parseInt(value));
                    break;
                case ("description"):
                    productInformation.setDescription(value);
                    break;
                case ("price"):
                    if (value.compareTo("null") != 0) {
                        productInformation.setPrice(Integer.parseInt(value));
                    }
                    break;
                case ("priceWithDiscount"):
                    if (value.compareTo("null") != 0) {
                        productInformation.setTotalPriceWithDiscount(Integer.parseInt(value));
                    }
                    break;
                case ("totalPrice"):
                    if (value.compareTo("null") != 0) {
                        productInformation.setTotalPrice(Integer.parseInt(value));
                    }
                    break;
                case ("totalPriceWithDiscount"):
                    if (value.compareTo("null") != 0) {
                        productInformation.setPriceWithDiscount(Integer.parseInt(value));
                    }
                    break;
                case ("checkId"):
                    if (value.compareTo("null") != 0) {
                        productInformation.setCheckId(Integer.parseInt(value));
                    }
                    break;
            }
        });
        return productInformation;
    }
}

package ru.clevertec.check.runner.streamIO.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.DiscountCard;

@Component
public class DiscountCardIO extends StreamIO{

    private final static String LINK_ADDRESS =
            "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\DiscountCardFile.CSV";

    public DiscountCardIO() {
        super(LINK_ADDRESS);
    }

    @Override
    protected Object objectAssembly(String text) {
        DiscountCard discountCard = new DiscountCard();
        super.parseMap(text).forEach((key, value) -> {
            switch (key) {
                case ("DiscountCard id"):
                    discountCard.setId(Integer.parseInt(value));
                    break;
                case ("discount"):
                    discountCard.setDiscount(Integer.parseInt(value));
                    break;
            }
        });
        return discountCard;
    }
}

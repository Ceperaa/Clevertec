package ru.clevertec.check.runner.streamIO.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.Check;

@Component
public class CheckIO extends StreamIO {

    private final static String LINK_ADDRESS =
            "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\CheckFile.CSV";


    public CheckIO() {
        super(LINK_ADDRESS);
    }

    @Override
    protected Check objectAssembly(String text) {
        Check check = new Check();
        super.parseMap(text).forEach((key, value) -> {
            switch (key) {
                case ("Check id"):
                    check.setId(Integer.parseInt(value));
                    break;
                case ("totalPriceWithDiscount"):
                    check.setTotalPriceWithDiscount(Integer.parseInt(value));
                    break;
                case ("totalPrice"):
                    check.setTotalPrice(Integer.parseInt(value));
                    break;
                case ("discountAmount"):
                    if (value.compareTo("null") != 0) {
                        check.setDiscountAmount(Integer.parseInt(value));
                    }
                    break;
                case ("totalPercent"):
                    if (value.compareTo("null") != 0) {
                        check.setTotalPercent(Integer.parseInt(value));
                    }
                    break;
            }
        });
        return check;
    }
}

package ru.clevertec.check.runner.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.check.runner.dto.ProductDto;

import java.util.List;

@Data
@NoArgsConstructor
public class Check {

    private long id;
    private List<ProductDto> productList;
    private double totalPriceWithDiscount;
    private double totalPrice;
    private double discountAmount;
    private int totalPercent;

    @Override
    public String toString() {
        return "Check " +
                "id=" + id +
                ", totalPriceWithDiscount=" + totalPriceWithDiscount +
                ", productList=" + productList +
                ", totalPrice='" + totalPrice +
                ", discountAmount=" + discountAmount +
                ", totalPercent=" + totalPercent +
                "";
    }
}

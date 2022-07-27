package ru.clevertec.check.runner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Check {

    private long id;
    private transient List<ProductInformation> productList;
    private double totalPriceWithDiscount;
    private double totalPrice;
    private double discountAmount;
    private int totalPercent;

    @Override
    public String toString() {
        return "Check " +
                "id=" + id +
                ", totalPriceWithDiscount=" + totalPriceWithDiscount +
                ", totalPrice='" + totalPrice +
                ", discountAmount=" + discountAmount +
                ", totalPercent=" + totalPercent +
                "";
    }
}

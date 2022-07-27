package ru.clevertec.check.runner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInformation {

    private long id;
    private double priceWithDiscount;
    private double totalPrice;
    private double totalPriceWithDiscount;
    private Integer discountPercent;
    private int amount;
    private transient Product product;
    private transient Check check;

    @Override
    public String toString() {
        return "ProductInformation "
                + "id=" + id
                + ", totalPrice=" + totalPrice
                + ", totalPriceWithDiscount=" + totalPriceWithDiscount
                + ", discountPercent=" + discountPercent
                + ", product=" + product
                // + ", checkId=" + checkId
                + "";
    }
}

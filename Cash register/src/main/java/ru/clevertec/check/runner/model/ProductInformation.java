package ru.clevertec.check.runner.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ProductInformation {

    private long id;
    @NonNull
    private Double priceWithDiscount;
    private double totalPrice;
    private double totalPriceWithDiscount;
    @NonNull
    private Integer discountPercent;
    private Product product;

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

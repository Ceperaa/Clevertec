package ru.clevertec.check.runner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoForCreate {

    private String name;
    private String amount;
    private Integer discountPercent;
    private String price;

    @Override
    public String toString() {
        return "Product "
                + "name=" + name
                + ", amount=" + amount
                + ", price=" + price
                + ", discountPercent=" + discountPercent
                + "";
    }
}

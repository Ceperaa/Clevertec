package ru.clevertec.check.runner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreatDto {

    private Long id;
    private String name;
    private String amount;
    private Integer discountPercent;
    private String price;
    private Long productInformationId;

    @Override
    public String toString() {
        return "Product "
                + "id=" + id
                + ", name=" + name
                + ", amount=" + amount
                + ", price=" + price
                + ", discountPercent=" + discountPercent
                + "";
    }
}

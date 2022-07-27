package ru.clevertec.check.runner.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String amount;
    private Integer discountPercent;
    private String price;
}

package ru.clevertec.check.runner.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String amount;
    private Integer discountPercent;
    private String price;
}

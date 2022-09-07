package ru.clevertec.check.runner.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoForSave {

    private String name;
    private String amount;
    private Integer discountPercent;
    private String price;
}

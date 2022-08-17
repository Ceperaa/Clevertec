package ru.clevertec.check.runner.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

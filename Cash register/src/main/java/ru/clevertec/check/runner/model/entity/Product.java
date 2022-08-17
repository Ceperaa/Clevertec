package ru.clevertec.check.runner.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String amount;
    private Integer discountPercent;
    private String price;
    private transient ProductInformation productInformation;
}

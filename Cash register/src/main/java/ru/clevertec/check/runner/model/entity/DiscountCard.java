package ru.clevertec.check.runner.model.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {

    private long id;
    private int discount;
}

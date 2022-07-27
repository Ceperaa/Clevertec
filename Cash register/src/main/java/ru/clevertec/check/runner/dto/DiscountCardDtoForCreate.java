package ru.clevertec.check.runner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCardDtoForCreate {

    private int discount;

    @Override
    public String toString() {
        return "DiscountCard " +
                "discount=" + discount +
                "";
    }
}

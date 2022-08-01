package ru.clevertec.check.runner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountCardDtoForSave {

    private int discount;

    @Override
    public String toString() {
        return "DiscountCard " +
                "discount=" + discount +
                "";
    }
}

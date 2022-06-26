package ru.clevertec.check.runner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {

    private long id;
    private int discount;

    @Override
    public String toString() {
        return "DiscountCard " +
                "id=" + id +
                ", discount=" + discount +
                "";
    }
}

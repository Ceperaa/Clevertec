package ru.clevertec.check.runner.model;

import jakarta.validation.constraints.Pattern;

public class DiscountCard {

    @Pattern(regexp = "//d")
    private long id;
    @Pattern(regexp = "^[0-9]{1,3}")
    private int discount;

    public DiscountCard(long id, int discount) {
        this.id = id;
        this.discount = discount;
    }

    public DiscountCard() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "DiscountCard " +
                "id=" + id +
                ", discount=" + discount +
                "";
    }
}

package ru.clevertec.check.runner.model;

public class DiscountCard {

    private long id;
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

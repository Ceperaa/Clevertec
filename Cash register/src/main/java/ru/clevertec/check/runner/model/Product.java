package ru.clevertec.check.runner.model;

import jakarta.validation.constraints.Pattern;

public class Product {

    private long id;
    @Pattern(regexp = "[A-Za-zА-Яа-я]", message = "invalid.amount")
    private String name;
    private int amount;
    private int discountPercent;
    private int price;

    public Product(long id, String name, int amount, int discountPercent, int price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.discountPercent = discountPercent;
        this.price = price;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product "
                + "id=" + id
                + ", name=" + name
                + ", amount=" + amount
                + ", price=" + price
                + ", discountPercent=" + discountPercent
                + "";
    }

}

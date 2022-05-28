package ru.clevertec.check.runner.model;

public class Product {

    private long id;
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
}

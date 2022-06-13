package ru.clevertec.check.runner.model;

public class Product {

    private long id;
    private String name;
    private String amount;
    private int discountPercent;
    private String price;

    public Product(long id, String name, String amount, int discountPercent, String price) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

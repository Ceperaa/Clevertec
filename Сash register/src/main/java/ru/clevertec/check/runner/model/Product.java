package ru.clevertec.check.runner.model;

public class Product {

    private long id;
    private String name;
    private int count;
    private int discountPercent;
    private int price;
    private int totalPriceForEverything;

    public Product(long id, String name, int count, int discountPercent, int price) {
        this.id = id;
        this.name = name;
        this.count = count;
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

    public int getTotalPriceForEverything() {
        return totalPriceForEverything;
    }

    public void setTotalPriceForEverything(int totalPriceForEverything) {
        this.totalPriceForEverything = totalPriceForEverything;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

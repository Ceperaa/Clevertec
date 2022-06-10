package ru.clevertec.check.runner.model;

import java.util.List;

public class Check {

    private long id;
    private List<ProductInformation> productList;
    private int totalPriceWithDiscount;
    private int totalPrice;
    private int discountAmount;
    private int totalPercent;

    public Check() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(int totalPercent) {
        this.totalPercent = totalPercent;
    }

    public int getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(int totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public List<ProductInformation> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductInformation> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Check " +
                "id=" + id +
                ", totalPriceWithDiscount=" + totalPriceWithDiscount +
                ", totalPrice='" + totalPrice +
                ", discountAmount=" + discountAmount +
                ", totalPercent=" + totalPercent +
                "";
    }
}
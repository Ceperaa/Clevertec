package ru.clevertec.check.runner.model;

import ru.clevertec.check.runner.dto.ProductDto;

import java.util.List;

public class Check {

    private long id;
    private List<ProductDto> productList;
    private double totalPriceWithDiscount;
    private double totalPrice;
    private double discountAmount;
    private int totalPercent;

    public Check() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(int totalPercent) {
        this.totalPercent = totalPercent;
    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(double totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public List<ProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDto> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Check " +
                "id=" + id +
                ", totalPriceWithDiscount=" + totalPriceWithDiscount +
                ", productList=" + productList +
                ", totalPrice='" + totalPrice +
                ", discountAmount=" + discountAmount +
                ", totalPercent=" + totalPercent +
                "";
    }
}

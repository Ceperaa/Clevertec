package ru.clevertec.check.runner.model;

import java.util.List;

public class Check {

    private long id;
    private List<Product> productList;
    private int TotalPriceWithDiscount;
    private int TotalPrice;

    public Check(long id, List<Product> productList, int totalPriceWithDiscount, int totalPrice) {
        this.id = id;
        this.productList = productList;
        TotalPriceWithDiscount = totalPriceWithDiscount;
        TotalPrice = totalPrice;
    }

    public int getTotalPriceWithDiscount() {
        return TotalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(int totalPriceWithDiscount) {
        TotalPriceWithDiscount = totalPriceWithDiscount;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

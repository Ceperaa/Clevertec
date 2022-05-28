package ru.clevertec.check.runner.model;

public class ProductInformation {

    private long id;
    private int qty;
    private String description;
    private int price;
    private int priceWithDiscount;
    private int totalPrice;
    private int totalPriceWithDiscount;
    private int discountPercent;

    public ProductInformation(String description, int price, int priceWithDiscount) {
        this.description = description;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    public ProductInformation() {
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(int priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public int getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(int totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ProductInformation " +
                "id=" + id +
                ", qty=" + qty +
                ", description='" + description +
                ", price=" + price +
                ", priceWithDiscount=" + priceWithDiscount +
                ", totalPrice=" + totalPrice +
                ", totalPriceWithDiscount=" + totalPriceWithDiscount +
                "";
    }
}

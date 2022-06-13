package ru.clevertec.check.runner.dto;

public class ProductDto {

    private long id;
    private int qty;
    private String description;
    private String price;
    private double priceWithDiscount;
    private double totalPrice;
    private double totalPriceWithDiscount;
    private int discountPercent;
    private long checkId;

    public ProductDto(String description, String price, double priceWithDiscount) {
        this.description = description;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    public long getCheckId() {
        return checkId;
    }

    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }

    public ProductDto() {
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

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(double totalPriceWithDiscount) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
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
                ", checkId=" + checkId +
                "";
    }

}

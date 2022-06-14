package ru.clevertec.check.runner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
//@EqualsAndHashCode
public class ProductDto {

    private long id;
    private int qty;
    @NonNull
    private String description;
    @NonNull
    private String price;
    @NonNull
    private Double priceWithDiscount;
    private double totalPrice;
    private double totalPriceWithDiscount;
    private int discountPercent;
    private long checkId;

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

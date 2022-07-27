package ru.clevertec.check.runner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductInformationDto {

    //private long id;
    //private int qty;
    @NonNull
    private String name;
    private String amount;
    @NonNull
    private String price;
    @NonNull
    private Double priceWithDiscount;
    private double totalPrice;
    private double totalPriceWithDiscount;
    private int discountPercent;
   // private long checkId;

//    @Override
//    public String toString() {
//        return "ProductInformation " +
//                //"id=" + id +
//                //", qty=" + qty +
//                ", name='" + name +
//                ", price=" + price +
//                ", priceWithDiscount=" + priceWithDiscount +
//                ", totalPrice=" + totalPrice +
//                ", totalPriceWithDiscount=" + totalPriceWithDiscount +
//               // ", checkId=" + checkId +
//                "";
//    }

}

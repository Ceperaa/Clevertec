package ru.clevertec.check.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductInformationDto {

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
}

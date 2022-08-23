package ru.clevertec.check.runner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
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

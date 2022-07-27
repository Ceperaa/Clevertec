package ru.clevertec.check.runner.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckDto {

    private long id;
    private List<ProductInformationDto> productList;
    private double totalPriceWithDiscount;
    private double totalPrice;
    private double discountAmount;
    private int totalPercent;
}

package ru.clevertec.check.runner.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckDto {

    private long id;
    private List<ProductInformationDto> productList;
    private LocalDateTime date;
    private double totalPriceWithDiscount;
    private double totalPrice;
    private double discountAmount;
    private int totalPercent;
}

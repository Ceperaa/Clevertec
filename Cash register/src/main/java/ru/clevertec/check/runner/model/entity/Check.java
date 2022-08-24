package ru.clevertec.check.runner.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(includeFieldNames = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "public.check")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "check", fetch = FetchType.EAGER)
    private transient List<ProductInformation> productList;
    @Column(name = "total_price_with_discount")
    private double totalPriceWithDiscount;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "discount_amount")
    private double discountAmount;
    @Column(name = "total_percent")
    private int totalPercent;
}

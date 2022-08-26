package ru.clevertec.check.runner.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_information")
public class ProductInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "price_with_discount")
    private double priceWithDiscount;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "total_price_with_discount")
    private double totalPriceWithDiscount;
    @Column(name = "discount_percent")
    private Integer discountPercent;
    @Column
    private int amount;

    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn//(name = "product_id")
    @ToString.Exclude
    @JsonIgnore
    private Product product;

    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY/*,optional = false*/)
    @JoinColumn//(name = "check_id")
    @ToString.Exclude
    @JsonIgnore
    private Check check;
}
//cascade=CascadeType.ALL - джойны тоже добаятся

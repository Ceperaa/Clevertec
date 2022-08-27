package ru.clevertec.check.runner.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class ProductInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private double priceWithDiscount;
    @Column
    private double totalPrice;
    @Column
    private double totalPriceWithDiscount;
    @Column
    private Integer discountPercent;
    @Column
    private int amount;

    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn
    @ToString.Exclude
    @JsonIgnore
    private Product product;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn
    private Check check;
}

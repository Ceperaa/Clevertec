package ru.clevertec.check.runner.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private double totalPriceWithDiscount;
    @Column
    private double totalPrice;
    @Column
    private double discountAmount;
    @Column
    private int totalPercent;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "check", fetch = FetchType.LAZY)
    private List<ProductInformation> productList;
}

package ru.clevertec.check.runner.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalPriceWithDiscount;
    private LocalDateTime date;
    private double totalPrice;
    private double discountAmount;
    private int totalPercent;

    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "check_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductInformation> productList;
}

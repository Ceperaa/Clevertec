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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String amount;
    @Column
    private Integer discountPercent;
    @Column
    private String price;

    @ToString.Exclude
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonIgnore
    private List< ProductInformation> productInformation;
}

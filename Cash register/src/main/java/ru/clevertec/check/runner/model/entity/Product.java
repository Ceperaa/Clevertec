package ru.clevertec.check.runner.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "public.product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private String amount;
    @Column(name = "discount_percent")
    private Integer discountPercent;
    @Column(name = "price")
    private String price;
    @OneToOne(mappedBy = "product",fetch = FetchType.EAGER)
    private transient ProductInformation productInformation;
}

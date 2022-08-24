package ru.clevertec.check.runner.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(includeFieldNames = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "public.product_information")
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
    @Column(name = "amount")
    private int amount;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private transient Product product;
    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "check_id")
    private transient Check check;
}

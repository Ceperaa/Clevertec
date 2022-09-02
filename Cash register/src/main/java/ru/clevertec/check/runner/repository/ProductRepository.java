package ru.clevertec.check.runner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.check.runner.model.dto.MaxOfSaleProductDto;
import ru.clevertec.check.runner.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // продукт который чаще всего продавался
    @Query(nativeQuery = true, value = "" +
            " select p.id, p.amount, p.name, p.price, count(pi.product_id) as countsSale" +
            " from Product p" +
            "         join product_information pi on p.id = pi.product_id" +
            " group by pi.product_id, p.id, p.amount, p.name, p.price" +
            " order by countsSale desc" +
            " limit 1")
    MaxOfSaleProductDto findByProduct();

}

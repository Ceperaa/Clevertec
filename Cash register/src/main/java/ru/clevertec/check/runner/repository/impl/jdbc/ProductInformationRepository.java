package ru.clevertec.check.runner.repository.impl.jdbc;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.entity.ProductInformation;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.EntityManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductInformationRepository extends AbstractRepository<ProductInformation> {

    private static final String SELECT =
            "SELECT id, price_with_discount,check_id, product_id, total_price, total_price_with_discount,discount_percent" +
                    " FROM product_information WHERE id = ?";
    private static final String INSERT =
            "INSERT INTO public.product_information (price_with_discount,check_id, product_id, total_price, total_price_with_discount,discount_percent)" +
                    " VALUES (?,?, ?,?, ?, ?)";
    private static final String UPDATE =
            "UPDATE product_information " +
                    "SET price_with_discount =?,check_id =?,total_price =?,total_price_with_discount =?,discount_percent =? " +
                    "WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM product_information WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT id, price_with_discount,check_id, product_id, total_price, total_price_with_discount,discount_percent " +
                    "FROM product_information " +
                    "ORDER BY id ASC LIMIT ? OFFSET ?";

    public ProductInformationRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL, getConnection);
    }

    protected Long getId(ProductInformation productInformation) {
        return productInformation.getId();
    }

    protected ProductInformation setId(ProductInformation product, long id) {
        product.setId(id);
        return product;
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected ProductInformation resultOrder(ResultSet resultSet) {
        return ProductInformation
                .builder()
                .id(resultSet.getLong("id"))
                .priceWithDiscount(Double.parseDouble(resultSet.getString("price_with_discount")))
                .totalPrice(Double.parseDouble(resultSet.getString("total_price")))
                .totalPriceWithDiscount(Double.parseDouble(resultSet.getString("total_price_with_discount")))
                .discountPercent(resultSet.getInt("discount_percent"))
                .build();
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected int statementOrder(PreparedStatement statement, ProductInformation model) {
        final int COUNT_FIELD = 6;
        statement.setBigDecimal(1, BigDecimal.valueOf(model.getPriceWithDiscount()));
        statement.setLong(2, model.getCheck().getId());
        statement.setLong(3, model.getProduct().getId());
        statement.setBigDecimal(4, BigDecimal.valueOf(model.getTotalPrice()));
        statement.setBigDecimal(5, BigDecimal.valueOf(model.getTotalPriceWithDiscount()));
        statement.setInt(6, model.getDiscountPercent());
        return COUNT_FIELD;
    }
}

package ru.clevertec.check.runner.repository.impl.jdbc;

import lombok.SneakyThrows;
import ru.clevertec.check.runner.model.entity.Check;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.EntityManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Repository
public class CheckRepository extends AbstractRepository<Check> {


    private static final String SELECT =
            "SELECT id, total_price_with_discount, total_price, discount_amount, total_percent " +
                    "FROM check " +
                    "WHERE id = ?";
    private static final String INSERT =
            "INSERT INTO public.check (total_price_with_discount, total_price, discount_amount, total_percent) " +
                    "VALUES (? , ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE check " +
                    "SET total_price_with_discount =?,total_price =?,discount_amount =?,total_percent =? " +
                    "WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM check WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT id, total_price_with_discount, total_price, discount_amount, total_percent" +
                    " FROM check " +
                    "ORDER BY id ASC LIMIT ? OFFSET ?";

    public CheckRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL, getConnection);
    }


    protected Long getId(Check product) {
        return product.getId();
    }

    protected Check setId(Check product, long id) {
        product.setId(id);
        return product;
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected Check resultOrder(ResultSet resultSet) {
        return Check.builder()
                .id(resultSet.getLong("id"))
                .discountAmount(resultSet.getDouble("discount_amount"))
                .totalPercent(resultSet.getInt("total_percent"))
                .totalPriceWithDiscount(Double.parseDouble(resultSet.getString("total_price_with_discount")))
                .totalPrice(Double.parseDouble(resultSet.getString("total_price")))
                .build();
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected int statementOrder(PreparedStatement statement, Check model) {
        final int COUNT_FIELD = 4;
        statement.setBigDecimal(1, BigDecimal.valueOf(model.getTotalPriceWithDiscount()));
        statement.setBigDecimal(2, BigDecimal.valueOf(model.getTotalPrice()));
        statement.setInt(3, (int) model.getDiscountAmount());
        statement.setInt(4, model.getTotalPercent());
        return COUNT_FIELD;
    }
}

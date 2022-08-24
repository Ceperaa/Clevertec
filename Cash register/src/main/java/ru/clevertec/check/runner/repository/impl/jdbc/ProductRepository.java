package ru.clevertec.check.runner.repository.impl.jdbc;

import lombok.SneakyThrows;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.EntityManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Repository
public class ProductRepository extends AbstractRepository<Product> implements RepositoryEntity<Product> {

    private static final String SELECT =
            "SELECT id,name, amount, price,discount_percent" +
                    " FROM product" +
                    " WHERE id = ?";
    private static final String INSERT =
            "INSERT INTO product (name, amount, price,discount_percent)" +
                    " VALUES (?,?,?,?)";
    private static final String UPDATE =
            "UPDATE product " +
                    "SET name =?,amount =?,price =?,discount_percent =? " +
                    "WHERE (id = ?)";
    private static final String DELETE =
            "DELETE FROM product" +
                    " WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT id,name, amount, price,discount_percent" +
                    " FROM product " +
                    "ORDER BY id ASC LIMIT ? OFFSET ?";

    public ProductRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL, getConnection);
    }

    protected Long getId(Product product) {
        return product.getId();
    }

    protected Product setId(Product product, long id) {
        product.setId(id);
        return product;
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected Product resultOrder(ResultSet resultSet) {
        return Product
                .builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .discountPercent(resultSet.getInt("discount_percent"))
                .price(resultSet.getString("price"))
                .amount(resultSet.getString("amount"))
                .build();
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected int statementOrder(PreparedStatement statement, Product model) {
        final int COUNT_FIELD = 4;
        statement.setString(1, model.getName());
        statement.setInt(2, Integer.parseInt(model.getAmount()));
        statement.setBigDecimal(3, BigDecimal.valueOf(Double.parseDouble(model.getPrice())));
        statement.setInt(4, model.getDiscountPercent());
        return COUNT_FIELD;
    }
}

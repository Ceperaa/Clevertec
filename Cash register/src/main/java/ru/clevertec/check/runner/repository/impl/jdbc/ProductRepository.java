package ru.clevertec.check.runner.repository.impl.jdbc;

import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.EntityManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductRepository extends AbstractRepository<Product> implements RepositoryEntity<Product> {

    private final EntityManager getConnection;
    private static final String SELECT = "SELECT * FROM product WHERE id = ?";
    private static final String INSERT = "INSERT INTO product (name, amount, price,discount_percent) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE product SET name =?,amount =?,price =?,discount_percent =? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM product WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM product";

    public ProductRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL);
        this.getConnection = getConnection;
    }

    protected Long getId(Product product){
        return product.getId();
    }

    protected Product setId(Product product,long id){
        product.setId(id);
        return product;
    }


        @Override
    protected Product resultOrder(ResultSet resultSet) throws SQLException {
       return Product
                .builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .discountPercent(resultSet.getInt("discount_percent"))
                .price(Double.parseDouble(resultSet.getString("price")))
                .amount(resultSet.getString("amount"))
                .build();
    }

    @Override
    protected int statementOrder(PreparedStatement statement, Product model) throws SQLException {
        final int COUNT_FIELD = 4;
        statement.setString(1, model.getName());
        statement.setInt(2, Integer.parseInt(model.getAmount()));
        statement.setBigDecimal(3, BigDecimal.valueOf(model.getPrice()));
        statement.setInt(4, model.getDiscountPercent());
        return COUNT_FIELD;
    }

    @Override
    protected Connection getConnects() throws SQLException {
        try {
            return getConnection.getConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

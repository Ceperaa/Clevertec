package ru.clevertec.check.runner.repository.impl.jdbc;

import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.EntityManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductInformationRepository extends AbstractRepository<ProductInformation> {

    private final EntityManager getConnection;
    private static final String SELECT = "SELECT * FROM product_information WHERE id = ?";
    private static final String INSERT = "INSERT INTO public.product_information (price_with_discount,check_id, product_id, total_price, total_price_with_discount,discount_percent) VALUES (?,?, ?,?, ?, ?)";
    private static final String UPDATE = "UPDATE product_information SET price_with_discount =?,check_id =?,total_price =?,total_price_with_discount =?,discount_percent =? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM product_information WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM product_information";


    public ProductInformationRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL);
        this.getConnection = getConnection;
    }

    protected Long getId(ProductInformation productInformation) {
        return productInformation.getId();
    }

    protected ProductInformation setId(ProductInformation product, long id) {
        product.setId(id);
        return product;
    }

    @Override
    protected ProductInformation resultOrder(ResultSet resultSet) throws SQLException {
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
    protected int statementOrder(PreparedStatement statement, ProductInformation model) throws SQLException {
        final int COUNT_FIELD = 6;
        statement.setBigDecimal(1, BigDecimal.valueOf(model.getPriceWithDiscount()));
        statement.setLong(2, model.getCheck().getId());
        statement.setLong(3, model.getProduct().getId());
        statement.setBigDecimal(4, BigDecimal.valueOf(model.getTotalPrice()));
        statement.setBigDecimal(5, BigDecimal.valueOf(model.getTotalPriceWithDiscount()));
        statement.setInt(6, model.getDiscountPercent());
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

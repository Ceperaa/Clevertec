package ru.clevertec.check.runner.repository.impl.jdbc;

import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DiscountCardRepository extends AbstractRepository<DiscountCard> {

    private final EntityManager getConnection;
    private static final String SELECT = "SELECT * FROM discount_card WHERE id = ?";
    private static final String INSERT = "INSERT INTO public.discount_card (discount) VALUES (?)";
    private static final String UPDATE = "UPDATE discount_card SET discount = ? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM discount_card WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM discount_card";

    public DiscountCardRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL);
        this.getConnection = getConnection;
    }

    protected Long getId(DiscountCard discountCard){
        return discountCard.getId();
    }

    protected DiscountCard setId(DiscountCard discountCard,long id){
        discountCard.setId(id);
        return discountCard;
    }

    @Override
    protected DiscountCard resultOrder(ResultSet resultSet) throws SQLException {
        return DiscountCard
                .builder()
                .id(resultSet.getLong("id"))
                .discount(Integer.parseInt(resultSet.getString("discount")))
                .build();
    }

    @Override
    protected int statementOrder(PreparedStatement statement, DiscountCard model) throws SQLException {
        final int COUNT_FIELD = 1;
        statement.setInt(1, model.getDiscount());
        return COUNT_FIELD;
    }

    @Override
    protected Connection getConnects()  {
        try {
            return getConnection.getConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

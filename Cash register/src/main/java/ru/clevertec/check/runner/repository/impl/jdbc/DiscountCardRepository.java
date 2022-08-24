package ru.clevertec.check.runner.repository.impl.jdbc;

import lombok.SneakyThrows;
import ru.clevertec.check.runner.model.entity.DiscountCard;
import ru.clevertec.check.runner.repository.impl.jdbc.connector.EntityManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Repository
public class DiscountCardRepository extends AbstractRepository<DiscountCard> {

    private static final String SELECT = "SELECT id, discount FROM discount_card WHERE id = ?";
    private static final String INSERT = "INSERT INTO public.discount_card (discount) VALUES (?)";
    private static final String UPDATE = "UPDATE discount_card SET discount = ? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM discount_card WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, discount FROM discount_card ORDER BY id ASC LIMIT ? OFFSET ?";

    public DiscountCardRepository(EntityManager getConnection) {
        super(SELECT, INSERT, UPDATE, DELETE, SELECT_ALL, getConnection);
    }

    protected Long getId(DiscountCard discountCard){
        return discountCard.getId();
    }

    protected DiscountCard setId(DiscountCard discountCard,long id){
        discountCard.setId(id);
        return discountCard;
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected DiscountCard resultOrder(ResultSet resultSet) {
        return DiscountCard
                .builder()
                .id(resultSet.getLong("id"))
                .discount(Integer.parseInt(resultSet.getString("discount")))
                .build();
    }

    @Override
    @SneakyThrows(SQLException.class)
    protected int statementOrder(PreparedStatement statement, DiscountCard model) {
        final int COUNT_FIELD = 1;
        statement.setInt(1, model.getDiscount());
        return COUNT_FIELD;
    }
}

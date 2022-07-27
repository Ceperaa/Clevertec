package ru.clevertec.check.runner.repository.impl.jdbc;


import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.Transactional;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T> implements RepositoryEntity<T> {

    private final String select;
    private final String insert;
    private final String update;
    private final String delete;
    private final String selectAll;

    public AbstractRepository(
            String select
            , String insert
            , String update
            , String delete
            , String selectAll
    ) {
        this.select = select;
        this.insert = insert;
        this.update = update;
        this.delete = delete;
        this.selectAll = selectAll;
    }

    @Transactional
    public T add(T model) throws SQLException {
        Connection connection = getConnects();
        try (PreparedStatement statement = connection.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS)) {
            statementOrder(statement, model);
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return setId(resultOrder(resultSet), resultSet.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    @Transactional
    public List<T> findAll() throws SQLException {
        List<T> list = new LinkedList<>();

        try (PreparedStatement statement = getConnects()
                .prepareStatement(selectAll)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(resultOrder(resultSet));
            }
        }
        return list;
    }

    @Override
    @Transactional
    public Optional findById(Long key) throws SQLException {
        try (PreparedStatement statement = getConnects().prepareStatement(select)) {
            statement.setInt(1, Math.toIntExact(key));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                T model = resultOrder(resultSet);
                return Optional.ofNullable(model);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public T update(T model) throws SQLException {
        try (PreparedStatement statement = getConnects().prepareStatement(update)) {
            int countFields = statementOrder(statement, model);
            statement.setInt(countFields + 1, Math.toIntExact(getId(model)));
            statement.executeUpdate();
        }
        return model;
    }

    @Override
    @Transactional
    public void delete(long id) throws SQLException {
        try (PreparedStatement statement = getConnects()
                .prepareStatement(delete)) {
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
        }
    }

    abstract protected Long getId(T model) throws SQLException;

    abstract protected T setId(T model, long id) throws SQLException;

    abstract protected T resultOrder(ResultSet resultSet) throws SQLException;

    abstract protected int statementOrder(PreparedStatement statement, T model)
            throws SQLException;

    abstract protected Connection getConnects() throws SQLException;
}

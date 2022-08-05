package ru.clevertec.check.runner.repository.impl.jdbc;


import lombok.SneakyThrows;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.impl.jdbc.transactional.Transactional;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

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
    private static final int DEFAULT_PAGE_SIZE = 20;

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
    @SneakyThrows(SQLException.class)
    public T add(T model) {
        Connection connection = getConnects();
        try (PreparedStatement statement = connection.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS)) {
            statementOrder(statement, model);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return setId(resultOrder(resultSet), resultSet.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    @Override
    @Transactional
    @SneakyThrows(SQLException.class)
    public List<T> findAll(Integer limit, int offset) {
        if (limit == 0) {
            limit = DEFAULT_PAGE_SIZE;
        }
        List<T> list = new LinkedList<>();
        try (PreparedStatement statement = getConnects()
                .prepareStatement(selectAll)) {
            statement.setInt(1,limit);
            statement.setInt(2,offset);
            statement.execute();
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(resultOrder(resultSet));
            }
        }
        return list;
    }

    @Override
    @Transactional
    @SneakyThrows(SQLException.class)
    public Optional findById(Long key) {
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
    @SneakyThrows(SQLException.class)
    public T update(T model) {
        try (PreparedStatement statement = getConnects().prepareStatement(update)) {
            int countFields = statementOrder(statement, model);
            statement.setInt(countFields + 1, Math.toIntExact(getId(model)));
            statement.executeUpdate();
        }
        return model;
    }

    @Override
    @Transactional
    @SneakyThrows(SQLException.class)
    public void delete(long id) throws ObjectNotFoundException {
        try (PreparedStatement statement = getConnects()
                .prepareStatement(delete)) {
            statement.setInt(1, Math.toIntExact(id));
            if (statement.executeUpdate() == 0) {
                throw new ObjectNotFoundException("object with id " + id + " not found", id);
            }
        }
    }

    abstract protected Long getId(T model);

    abstract protected T setId(T model, long id);

    abstract protected T resultOrder(ResultSet resultSet);

    abstract protected int statementOrder(PreparedStatement statement, T model);

    abstract protected Connection getConnects();
}

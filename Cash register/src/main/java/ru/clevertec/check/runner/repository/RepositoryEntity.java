package ru.clevertec.check.runner.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RepositoryEntity<E> {

   List<E> findAll() throws SQLException, IOException;

   E add(E o) throws IOException, SQLException;

   void delete(long id) throws SQLException, IOException;

   Optional<E> findById(Long id) throws SQLException;

   E update(E o) throws SQLException, IOException;
}

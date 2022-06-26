package ru.clevertec.check.runner.repository;

import java.util.List;

public interface RepositoryEntity<E> {

   List<E> findAll() throws Exception;

   E add(E o) throws Exception;

   void delete(long id) throws Exception;

   E findById(Long id);

   E update(E o) throws Exception;
}

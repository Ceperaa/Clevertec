package ru.clevertec.check.runner.repository;

import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RepositoryEntity<E> {

    List<E> findAll(int limit, int offset);

    E add(E o);

    void delete(long id) throws ObjectNotFoundException;

    Optional<E> findById(Long id);

    E update(E o);
}

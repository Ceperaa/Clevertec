package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;

public interface EntityServiceCrud<T> {

    T findById(Long id) throws ObjectNotFoundException;

    List<T> allList(int offset, int limit);

    T create(Object productDtoForSave);

    void delete(long id) throws ObjectNotFoundException;

    T update(Object product) throws ObjectNotFoundException;
}

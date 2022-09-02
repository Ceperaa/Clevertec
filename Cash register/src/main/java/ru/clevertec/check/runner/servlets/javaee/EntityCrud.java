package ru.clevertec.check.runner.servlets.javaee;

import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EntityCrud {

    Object createObject(Object o);

    Object updateObject(Object o) throws ObjectNotFoundException;

    void deleteObject(long id) throws ObjectNotFoundException;

    Optional findByObjectId(long id) throws ObjectNotFoundException;

    List<Object> findAllObject(int offset, Integer limit);

}

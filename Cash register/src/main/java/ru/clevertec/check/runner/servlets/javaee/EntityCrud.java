package ru.clevertec.check.runner.servlets.javaee;

import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EntityCrud {

   Object createObject(Object o) throws IOException, SQLException;

   Object updateObject(Object o) throws IOException, SQLException, ObjectNotFoundException;

    void deleteObject(long id) throws ObjectNotFoundException;

    Optional findByObjectId(long id) throws SQLException, ObjectNotFoundException;

    List<Object> findAllObject(int offset, Integer limit) throws IOException, SQLException;

}

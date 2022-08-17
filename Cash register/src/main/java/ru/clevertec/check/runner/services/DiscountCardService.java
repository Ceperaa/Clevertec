package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.sql.SQLException;

public interface DiscountCardService {

    DiscountCard findById(long id) throws SQLException, ObjectNotFoundException;
}

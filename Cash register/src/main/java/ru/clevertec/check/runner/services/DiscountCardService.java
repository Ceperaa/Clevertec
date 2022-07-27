package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.DiscountCardDtoForCreate;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DiscountCardService {

    Optional<DiscountCard> findById(long id) throws SQLException, ObjectNotFoundException;

    List<DiscountCard> allListDiscountCard() throws IOException, SQLException;

    DiscountCard saveCard(DiscountCardDtoForCreate card) throws IOException, SQLException;

    void deleteCard(long id) throws SQLException, ObjectNotFoundException, IOException;

}

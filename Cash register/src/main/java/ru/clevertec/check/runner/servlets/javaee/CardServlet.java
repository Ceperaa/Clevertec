package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/card/*"})
public class CardServlet extends AbstractEntityServlet {

    @Autowired
    private DiscountCardService cardService;

    @Override
    protected DiscountCardDtoForSave readerDiscountCard(BufferedReader reader) {
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
        return DiscountCardDtoForSave
                .builder()
                .discount(Integer.parseInt(jsonObject.get("discount").toString()))
                .build();
    }

    @Override
    protected Object createObject(Object o) throws IOException, SQLException {
        return cardService.saveCard((DiscountCardDtoForSave) o);
    }

    @Override
    protected Object updateObject(Object o) throws IOException, SQLException {
        return cardService.updateDiscountCard((DiscountCardDtoForSave) o);
    }

    @Override
    protected void deleteObject(long id) throws IOException, SQLException, ObjectNotFoundException {
        cardService.deleteCard(id);
    }

    @Override
    protected Optional findByObjectId(long id) throws SQLException, ObjectNotFoundException {
        return cardService.findById(id);
    }

    @Override
    protected List<Object> findAllObject(int offset, int limit) throws IOException, SQLException {
        return Collections.singletonList(cardService.allListDiscountCard(offset, limit));
    }
}

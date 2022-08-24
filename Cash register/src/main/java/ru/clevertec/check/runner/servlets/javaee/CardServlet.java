package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Servlet;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Servlet(url = "/card/*")
@RequiredArgsConstructor
public class CardServlet extends AbstractEntityServlet {

    private final DiscountCardService discountCardService;

    @Override
    public Object readObject(BufferedReader reader) {
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
        return DiscountCardDtoForSave
                .builder()
                .discount(Integer.parseInt(jsonObject.get("discount").toString()))
                .build();
    }

    @Override
    public Object createObject(Object o) {
        return discountCardService.saveCard((DiscountCardDtoForSave) o);
    }

    @Override
    public Object updateObject(Object o) {
        return discountCardService.updateDiscountCard((DiscountCardDtoForSave) o);
    }

    @Override
    public void deleteObject(long id) throws ObjectNotFoundException {
        discountCardService.deleteCard(id);
    }

    @Override
    public Optional findByObjectId(long id) {
        return discountCardService.findById(id);
    }

    @Override
    public List<Object> findAllObject(int offset, Integer limit) {
        return Collections.singletonList(discountCardService.allListDiscountCard(offset, limit));
    }
}

package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.services.EntityServiceCrud;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;

@Component
public class CardServlet extends AbstractEntityServlet<DiscountCard> {

    @Autowired
    @Qualifier("discountCardServiceImpl")
    private EntityServiceCrud discountCardEntityServiceCrud;

    @PostConstruct
    public void init(){
        setEntityServiceCrud(discountCardEntityServiceCrud);
    }

    @Override
    public DiscountCardDtoForSave readObject(BufferedReader reader) {
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
        return DiscountCardDtoForSave
                .builder()
                .discount(Integer.parseInt(jsonObject.get("discount").toString()))
                .build();
    }
}

package ru.clevertec.check.runner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.exception.ValidationException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/card")
public class DiscountCardController {

    private final DiscountCardService cardServices;

    public DiscountCardController(DiscountCardService cardServices) {
        this.cardServices = cardServices;
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCard add(DiscountCardDtoForSave discountCard) throws IOException, ValidationException, SQLException {
           return cardServices.saveCard(DataValidation.validator(discountCard));
    }

    @GetMapping("/all")
    public List<DiscountCard> all() throws IOException, SQLException {
        return cardServices.allListDiscountCard(10,10);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id)
            throws IOException, SQLException, ObjectNotFoundException {
        cardServices.deleteCard(id);
    }
}

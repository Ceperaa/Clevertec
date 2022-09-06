package ru.clevertec.check.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.services.DiscountCardService;
import ru.clevertec.check.util.exception.ObjectNotFoundException;
import ru.clevertec.check.util.exception.ValidationException;
import ru.clevertec.check.util.validation.DataValidation;

import java.io.IOException;
import java.util.List;

/**
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/card")
public class DiscountCardController {

    private final DiscountCardService cardServices;
    private static final String DEFAULT_LIMIT = "20";
    private static final String DEFAULT_OFFSET = "0";

    public DiscountCardController(DiscountCardService cardServices) {
        this.cardServices = cardServices;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCard add(@RequestBody DiscountCardDtoForSave discountCard) throws IOException, ValidationException {
        return cardServices.saveCard(DataValidation.validator(discountCard));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCard update(@RequestBody DiscountCard discountCard) throws IOException, ValidationException {
        return cardServices.updateDiscountCard(DataValidation.validator(discountCard));
    }

    @GetMapping
    public List<DiscountCard> all(@RequestParam(defaultValue = DEFAULT_LIMIT) int limit,
                                  @RequestParam(defaultValue = DEFAULT_OFFSET) int offset
    ) {
        return cardServices.allListDiscountCard(offset, limit);
    }

    @GetMapping("/{id}")
    public DiscountCard findById(@PathVariable long id) throws ObjectNotFoundException {
        return cardServices.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) throws ObjectNotFoundException {
        cardServices.deleteCard(id);
    }
}

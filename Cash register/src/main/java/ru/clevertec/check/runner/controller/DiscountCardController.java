package ru.clevertec.check.runner.controller;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.services.DiscountCardServices;

import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/card")
public class DiscountCardController {

    private final DiscountCardServices cardServices;

    public DiscountCardController(DiscountCardServices cardServices) {
        this.cardServices = cardServices;
    }

    @PutMapping("/add")
    public DiscountCard add(@RequestBody DiscountCard discountCard) throws Exception {
           return cardServices.saveCard(discountCard);
    }

    @GetMapping("/all")
    public List<DiscountCard> all() throws Exception {
        return cardServices.allListDiscountCard();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) throws Exception {
        cardServices.deleteCard(id);
    }
}

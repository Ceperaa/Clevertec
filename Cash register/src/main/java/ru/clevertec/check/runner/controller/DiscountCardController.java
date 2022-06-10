package ru.clevertec.check.runner.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.services.impl.DiscountCardServicesImpl;
import ru.clevertec.check.runner.streamIO.StreamEntityToString;

import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/card")
public class DiscountCardController {

    private final DiscountCardServicesImpl cardServices;
    private final StreamEntityToString streamEntityToString;

    public DiscountCardController(DiscountCardServicesImpl cardServices, StreamEntityToString streamEntityToString) {
        this.cardServices = cardServices;
        this.streamEntityToString = streamEntityToString;
    }

    @GetMapping("/add")
    public DiscountCard add(@Valid DiscountCard discountCard, BindingResult bindingResult) throws Exception {
       if (bindingResult.hasErrors()){
           streamEntityToString.fileOutputStream(List.of(bindingResult.getObjectName()),"E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\invalidData.txt",false);
           return null;
       } else {
           return cardServices.saveCard(discountCard);
       }
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

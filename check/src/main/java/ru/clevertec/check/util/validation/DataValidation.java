package ru.clevertec.check.util.validation;

import ru.clevertec.check.model.dto.DiscountCardDtoForSave;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.ProductDtoForSave;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.util.exception.ValidationException;
import ru.clevertec.check.util.streamIO.StreamEntityToString;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    private static final String INVALID_DATA_FILEPATH = "logs.files.invalidData.txt";


    public static List<String> validator(String[] s) throws Exception {
        Optional.ofNullable(s).orElseThrow(() -> new ServletException("no params"));
        List<String> list = new ArrayList<>();
        for (String value : s) {
            Pattern pattern = Pattern.compile("^([1-9]|[1-9]\\d|100)-([1-9]|[1][\\d]|20)$");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                list.add(value);
            } else {
                inputInvalidData(value);
                throw new ValidationException("не валид");
            }
        }
        return list;
    }

    public static ProductDtoForSave validator(ProductDtoForSave product) throws Exception {
        validatorHandler(product.getName(), "^[A-ZА-Я][a-zа-я]\\D{1,30}$");
        validatorHandler(product.getAmount(), "^([1-9]|[1][\\d]|20)$");
        validatorHandler(product.getPrice(), "^([1-9]|[1-9]\\d|100).\\d{2}$");
        validatorHandler(String.valueOf(product.getDiscountPercent()), "^(\\d{1,2}|100)$");
        return product;
    }

    public static ProductDto validator(ProductDto product) throws Exception {
        validatorHandler(product.getName(), "^[A-ZА-Я][a-zа-я]\\D{1,30}$");
        validatorHandler(product.getAmount(), "^([1-9]|[1][\\d]|20)$");
        validatorHandler(product.getPrice(), "^([1-9]|[1-9]\\d|100).\\d{2}$");
        validatorHandler(String.valueOf(product.getDiscountPercent()), "^(\\d{1,2}|100)$");
        return product;
    }

    public static DiscountCard validator(DiscountCard card) throws IOException, ValidationException {
        validatorHandler(card.toString(), "^DiscountCard\\sdiscount=([1-9]|[1-9]\\d|100)$");
        return card;
    }

    public static DiscountCardDtoForSave validator(DiscountCardDtoForSave cardDtoForCreate) throws IOException, ValidationException {
        validatorHandler(cardDtoForCreate.toString(), "^DiscountCard\\sdiscount=([1-9]|[1-9]\\d|100)$");
        return cardDtoForCreate;
    }

    private static void validatorHandler(String str, String patternString) throws ValidationException, IOException {
        Pattern pattern = Pattern.compile(
                patternString);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            inputInvalidData(str);
            throw new ValidationException("не валид");
        }
    }

    private static void inputInvalidData(String str) throws IOException {
        new StreamEntityToString().fileOutputStream(List.of(new Date().toString() + " | " + str),
                INVALID_DATA_FILEPATH ,
                false
        );
    }
}
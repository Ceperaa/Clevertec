package ru.clevertec.check.runner.util.validation;

import ru.clevertec.check.runner.dto.DiscountCardDtoForCreate;
import ru.clevertec.check.runner.dto.ProductDtoForCreate;
import ru.clevertec.check.runner.streamIO.StreamEntityToString;
import ru.clevertec.check.runner.util.exception.ValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static List<String> validator(String[] s) throws Exception {
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

    public static ProductDtoForCreate validator(ProductDtoForCreate product) throws Exception {
        validatorHandler(product,"^Product" +
                "\\sname=[A-ZА-Я][a-zа-я]\\D{1,30}," +
                "\\samount=([1-9]|[1][\\d]|20)," +
                "\\sprice=([1-9]|[1-9]\\d|100).\\d{2}," +
                "\\sdiscountPercent=(\\d{1,2}|100)$");
        return product;
    }

    public static DiscountCardDtoForCreate validator(DiscountCardDtoForCreate cardDtoForCreate) throws IOException, ValidationException {
        validatorHandler(cardDtoForCreate,"^DiscountCard\\sdiscount=([1-9]|[1-9]\\d|100)$");
        return cardDtoForCreate;
    }

    private static void validatorHandler(Object o,String patternString) throws ValidationException, IOException {
        String str = o.toString();
        Pattern pattern = Pattern.compile(
                patternString);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            inputInvalidData(str);
            throw new ValidationException("не валид");
        }
    }

    private static void inputInvalidData(String str) throws IOException {
        new StreamEntityToString().fileOutputStream(List.of(new Date().toString() + " | " + str)
                , "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\invalidData.txt"
                , false
        );
    }
}
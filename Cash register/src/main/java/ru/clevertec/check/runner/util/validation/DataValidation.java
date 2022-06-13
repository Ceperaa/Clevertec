package ru.clevertec.check.runner.util.validation;

import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.streamIO.StreamEntityToString;
import ru.clevertec.check.runner.util.exception.ValidationException;

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
            }
        }
        return list;
    }

    public static Product validator(Product product) throws Exception {
        String str = product.toString();
        Pattern pattern = Pattern.compile(
                "^Product\\sid=([1-9]|[1-9]\\d|100)," +
                        "\\sname=[A-ZА-Я][a-zа-я]\\D{1,30}," +
                        "\\samount=([1-9]|[1][\\d]|20)," +
                        "\\sprice=([1-9]|[1-9]\\d|100).\\d{2}," +
                        "\\sdiscountPercent=(\\d{1,2}|100)$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return product;
        }
        inputInvalidData(str);
        throw new ValidationException("не валид");
    }

    private static void inputInvalidData(String str) throws Exception {
        new StreamEntityToString().fileOutputStream(List.of(new Date().toString() + " | " + str)
                , "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\invalidData.txt"
                , false
        );
    }
}

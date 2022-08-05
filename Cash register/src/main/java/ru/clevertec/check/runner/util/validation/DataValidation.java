package ru.clevertec.check.runner.util.validation;

import ru.clevertec.check.runner.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.streamIO.StreamEntityToString;
import ru.clevertec.check.runner.util.exception.ValidationException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static int validatorHttpUrlSearchId(String url) throws IOException, ValidationException {
        int i = url.lastIndexOf("/") + 1;
        String st = url.substring(i);
        st = validatorString(st,"^([1-9]|[1-9]\\d|100)$");
        if (st.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(st);
        }
    }

    private static String validatorString(String str, String patternString) throws ValidationException, IOException {
        Pattern pattern = Pattern.compile(
                patternString);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public static List<String> validator(String[] s) throws Exception {
        Optional.ofNullable(s).orElseThrow(()-> new ServletException("no params"));
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
        validatorHandler(product.toString(), "^Product" +
                "\\sname=[A-ZА-Я][a-zа-я]\\D{1,30}," +
                "\\samount=([1-9]|[1][\\d]|20)," +
                "\\sprice=([1-9]|[1-9]\\d|100).\\d{2}," +
                "\\sdiscountPercent=(\\d{1,2}|100)$");
        return product;
    }

    public static DiscountCardDtoForSave validator(DiscountCardDtoForSave cardDtoForCreate) throws IOException, ValidationException {
        validatorHandler(cardDtoForCreate.toString(), "^DiscountCard\\sdiscount=([1-9]|[1-9]\\d|100)$");
        return cardDtoForCreate;
    }

    private static void validatorHandler(String str, String patternString) throws ValidationException, IOException {
        //String str = o.toString();
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
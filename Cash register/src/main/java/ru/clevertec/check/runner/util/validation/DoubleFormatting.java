package ru.clevertec.check.runner.util.validation;

public class DoubleFormatting {

    public static double formatting(Double d) {
        double de = d * 100;
        double result = Math.round(de) / 100.00;
        return result;
    }
}

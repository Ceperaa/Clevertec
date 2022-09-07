package ru.clevertec.check.runner.util.validation;

public class DoubleFormatting {

    public static double formatting(Double d) {
        double de = d * 100;
        return Math.round(de) / 100.00;
    }
}

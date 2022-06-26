package ru.clevertec.check.runner.reflection.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogObject {
    private String params;
    private Object returnParam;
}

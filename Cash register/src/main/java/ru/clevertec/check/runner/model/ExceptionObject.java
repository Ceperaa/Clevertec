package ru.clevertec.check.runner.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionObject {

    private int code;
    private String status;
    private String message;

}

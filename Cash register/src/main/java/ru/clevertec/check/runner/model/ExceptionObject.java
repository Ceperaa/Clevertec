package ru.clevertec.check.runner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionObject {

    private int code;
    private String status;
    private String exceptionClassName;
    private String message;
}

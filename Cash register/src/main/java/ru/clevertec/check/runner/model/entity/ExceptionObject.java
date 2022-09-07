package ru.clevertec.check.runner.model.entity;

import lombok.*;

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

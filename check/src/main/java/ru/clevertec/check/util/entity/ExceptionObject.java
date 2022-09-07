package ru.clevertec.check.util.entity;

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
    private String stackTrace;
    private String message;
}

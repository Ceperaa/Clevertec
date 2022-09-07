package ru.clevertec.check.runner.util.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogObject {
    private String methodName;
    private String params;
    private Object returnParam;
}

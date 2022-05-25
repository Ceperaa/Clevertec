package ru.clevertec.check.runner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.check.runner.model.ExceptionObject;

/**
 * Exceptions Handler
 *
 * @author Sergey Degtyarev
 */

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject response400(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ExceptionObject aggregate(String message, HttpStatus status) {
        ExceptionObject exceptionObject = new ExceptionObject();
        exceptionObject.setCode(status.value());
        exceptionObject.setStatus(String.valueOf(status));
        exceptionObject.setMessage(message);
        return exceptionObject;
    }
}

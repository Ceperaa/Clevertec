package ru.clevertec.check.runner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.check.runner.model.ExceptionObject;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.exception.ValidationException;

/**
 * Exceptions Handler
 *
 * @author Sergey Degtyarev
 */

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {
            ValidationException.class
            , Exception.class
            , ObjectNotFoundException.class
            , ValidationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject response400(@RequestBody Exception e) {
        return aggregate(e.getMessage());
    }

    private ExceptionObject aggregate(String message) {
        ExceptionObject exceptionObject = new ExceptionObject();
        exceptionObject.setCode(HttpStatus.BAD_REQUEST.value());
        exceptionObject.setStatus(String.valueOf(HttpStatus.BAD_REQUEST));
        exceptionObject.setMessage(message);
        return exceptionObject;
    }
}

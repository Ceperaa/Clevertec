package ru.clevertec.check.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.check.util.entity.ExceptionObject;
import ru.clevertec.check.util.exception.ObjectNotFoundException;
import ru.clevertec.check.util.exception.ValidationException;

import java.util.Arrays;

/**
 * Exceptions Handler
 *
 * @author Sergey Degtyarev
 */

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {
            Exception.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject response400(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.BAD_REQUEST, Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response422(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {
            ObjectNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionObject response404(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ExceptionObject aggregate(String message, HttpStatus status, String stackTrace) {
        return ExceptionObject
                .builder()
                .code(status.value())
                .stackTrace(stackTrace)
                .status(String.valueOf(status))
                .message(message)
                .build();
    }

    private ExceptionObject aggregate(String message, HttpStatus status) {
        return ExceptionObject
                .builder()
                .code(status.value())
                .status(String.valueOf(status))
                .message(message)
                .build();
    }
}

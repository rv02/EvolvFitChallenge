package com.rv02.evolvFit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final String NOT_FOUND_MESSAGE = "No Entry Match for Given Data";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
            javax.validation.ConstraintViolationException.class})
    public HashMap<String, String> handleValidationExceptions(Exception e) {
        HashMap<String, String> error = new HashMap<>();
        error.put("Reason", "Not Valid Json");
        error.put("error", e.toString());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({BlogNotFoundException.class})
    public HashMap<String, String> handleBlogNotFoundException(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND_MESSAGE);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
}

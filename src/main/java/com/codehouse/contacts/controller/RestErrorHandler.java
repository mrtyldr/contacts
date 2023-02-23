package com.codehouse.contacts.controller;

import com.codehouse.contacts.exceptions.AlreadyExistsException;
import com.codehouse.contacts.exceptions.NotFoundException;
import com.codehouse.contacts.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handle(Exception e){
        return ResponseEntity
                .status(NOT_IMPLEMENTED)
                .body(ErrorResponse.of(e.getMessage()));
    }
    @ExceptionHandler
    ResponseEntity<ErrorResponse> handle(AlreadyExistsException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(ErrorResponse.of(CONFLICT.value(),e.getMessage()));
    }
    @ExceptionHandler
    ResponseEntity<ErrorResponse> handle(NotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ErrorResponse.of(NOT_FOUND.value(),
                        e.getMessage(),"Required item cannot be found in db!"));
    }
}

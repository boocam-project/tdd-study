package com.swger.tddstudy.product.controller;

import javax.naming.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> bindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
    @ExceptionHandler
    public ResponseEntity<?> authenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.getMessage());
    }
}

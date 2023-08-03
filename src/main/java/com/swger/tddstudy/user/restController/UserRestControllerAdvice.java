package com.swger.tddstudy.user.restController;

import com.swger.tddstudy.user.exception.LoginFailureException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserRestControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> bindException(BindException e) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<?> illegalArgException(IllegalArgumentException e) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<?> loginFailureException(LoginFailureException e) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMessage);
    }
}

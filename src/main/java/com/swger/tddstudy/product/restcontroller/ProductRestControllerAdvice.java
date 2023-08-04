package com.swger.tddstudy.product.restcontroller;

import com.swger.tddstudy.product.exception.ProductSoldOutExcpetion;
import com.swger.tddstudy.product.exception.UnauthorizedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@RestControllerAdvice
public class ProductRestControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> unauthorizedException(UnauthorizedException e) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<?> productSoldOutException(ProductSoldOutExcpetion e) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMessage);
    }
}

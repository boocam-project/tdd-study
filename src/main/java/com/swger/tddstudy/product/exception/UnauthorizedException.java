package com.swger.tddstudy.product.exception;

import org.springframework.web.client.HttpClientErrorException.Unauthorized;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}

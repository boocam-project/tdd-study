package com.swger.tddstudy.user.service;

public class BadCredentialException extends RuntimeException{

    public BadCredentialException(String message) {
        super(message);
    }
}

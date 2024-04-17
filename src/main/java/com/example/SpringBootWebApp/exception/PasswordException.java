package com.example.SpringBootWebApp.exception;

public class PasswordException extends RuntimeException{
    public PasswordException(String msg) {
        super(msg);
    }
}

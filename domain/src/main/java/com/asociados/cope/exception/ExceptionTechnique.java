package com.asociados.cope.exception;

public class ExceptionTechnique extends RuntimeException {
    public ExceptionTechnique(String message, Exception e) {
        super(message, e);
    }
}
package com.asociados.cope.exception;

public class ValidateArgument {
    public static void validateRequired(Object value, String message) {
        if (value == null) {
            throw new ExceptionValueRequired(message);
        }
    }
}
package org.movier.exceptions;

public class MyRequestException extends RuntimeException {
    public MyRequestException(String message) {
        super(message);
    }
}

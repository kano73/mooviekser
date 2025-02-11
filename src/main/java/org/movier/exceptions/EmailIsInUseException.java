package org.movier.exceptions;

public class EmailIsInUseException extends RuntimeException {
    public EmailIsInUseException(String message) {
        super(message);
    }
}

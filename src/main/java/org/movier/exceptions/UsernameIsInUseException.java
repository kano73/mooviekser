package org.movier.exceptions;

public class UsernameIsInUseException extends RuntimeException {
    public UsernameIsInUseException(String message) {
        super(message);
    }
}

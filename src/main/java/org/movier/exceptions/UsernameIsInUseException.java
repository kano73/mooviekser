package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UsernameIsInUseException extends RuntimeException   implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }
    public UsernameIsInUseException(String message) {
        super(message);
    }
}

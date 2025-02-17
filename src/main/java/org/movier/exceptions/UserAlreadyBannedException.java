package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UserAlreadyBannedException extends RuntimeException implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }

    public UserAlreadyBannedException(String message) {
        super(message);
    }
}

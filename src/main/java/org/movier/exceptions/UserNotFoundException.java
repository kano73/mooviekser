package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserNotFoundException extends RuntimeException   implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}

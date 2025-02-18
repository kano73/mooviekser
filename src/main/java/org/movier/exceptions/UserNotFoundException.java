package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UserNotFoundException extends   CustomHandledException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

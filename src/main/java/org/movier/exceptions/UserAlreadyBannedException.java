package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UserAlreadyBannedException extends   CustomHandledException {

    public UserAlreadyBannedException(String message) {
        super(message);
    }
}

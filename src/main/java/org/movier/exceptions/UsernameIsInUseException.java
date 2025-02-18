package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UsernameIsInUseException extends   CustomHandledException {

    public UsernameIsInUseException(String message) {
        super(message);
    }
}

package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class IncorrectCredentialsException extends CustomHandledException {

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}

package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class IncorrectCredentialsException extends RuntimeException  implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }
    public IncorrectCredentialsException(String message) {
        super(message);
    }
}

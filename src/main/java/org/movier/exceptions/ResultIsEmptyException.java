package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class ResultIsEmptyException extends RuntimeException   implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }

    public ResultIsEmptyException(String message) {
        super(message);
    }
}

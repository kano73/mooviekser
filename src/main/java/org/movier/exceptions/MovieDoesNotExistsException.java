package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;
public class MovieDoesNotExistsException extends RuntimeException   implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }
    public MovieDoesNotExistsException(String message) {
        super(message);
    }
}

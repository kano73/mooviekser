package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class MovieDoesNotExistsException extends   CustomHandledException {

    public MovieDoesNotExistsException(String message) {
        super(message);
    }
}

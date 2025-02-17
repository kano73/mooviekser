package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class CommentNotFoundException extends RuntimeException   implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}

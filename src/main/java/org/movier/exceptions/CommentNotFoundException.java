package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class CommentNotFoundException extends CustomHandledException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}

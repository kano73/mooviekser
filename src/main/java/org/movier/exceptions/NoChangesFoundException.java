package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class NoChangesFoundException extends CustomHandledException {

    public NoChangesFoundException(String message) {
        super(message);
    }
}

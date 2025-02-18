package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class EmailIsInUseException extends  CustomHandledException {

    public EmailIsInUseException(String message) {
        super(message);
    }
}

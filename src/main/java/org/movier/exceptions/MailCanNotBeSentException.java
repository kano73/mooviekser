package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class MailCanNotBeSentException extends CustomHandledException {

    public MailCanNotBeSentException(String message) {
        super(message);
    }
}

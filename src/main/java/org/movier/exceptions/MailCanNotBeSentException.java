package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class MailCanNotBeSentException extends RuntimeException  implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }

    public MailCanNotBeSentException(String message) {
        super(message);
    }

    public MailCanNotBeSentException(String message, RuntimeException e) {
        super(message);
    }
}

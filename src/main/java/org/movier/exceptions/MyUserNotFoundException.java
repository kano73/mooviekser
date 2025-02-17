package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class MyUserNotFoundException extends RuntimeException  implements CustomHandledException {

    @Override
    public String handleException() {
        return super.getMessage();
    }

    public MyUserNotFoundException(String message) {
        super(message);
    }
}

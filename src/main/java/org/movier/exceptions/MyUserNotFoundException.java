package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class MyUserNotFoundException extends CustomHandledException {

    public MyUserNotFoundException(String message) {
        super(message);
    }
}

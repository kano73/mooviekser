package org.movier.exceptions.interfaces;

public class CustomHandledException extends RuntimeException {

    public String getCustomMessage() {
        return super.getMessage();
    }

    public CustomHandledException(String message) {
        super(message);
    }
}

package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PropertiesException extends RuntimeException implements CustomHandledException {

    @Override
    public ResponseEntity<String> handleException() {
        return new ResponseEntity<>("An server error accused", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public PropertiesException(String message) {
        super(message);
    }
}

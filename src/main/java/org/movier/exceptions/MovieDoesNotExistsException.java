package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MovieDoesNotExistsException extends RuntimeException implements CustomHandledException {

    @Override
    public ResponseEntity<String> handleException() {
        return new ResponseEntity<>("Movie does not exists", HttpStatus.CONFLICT);
    }
    public MovieDoesNotExistsException(String message) {
        super(message);
    }
}

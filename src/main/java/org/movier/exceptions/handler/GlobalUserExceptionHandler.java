package org.movier.exceptions.handler;

import org.movier.exceptions.interfaces.CustomHandledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalUserExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> EmailIsInUseException (RuntimeException exception){
        if (exception instanceof CustomHandledException customException) {
            return customException.handleException();
        }
        return new ResponseEntity<>("Error accused", HttpStatus.BAD_REQUEST);
    }

}

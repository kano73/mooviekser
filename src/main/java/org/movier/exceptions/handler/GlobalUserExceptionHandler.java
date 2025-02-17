package org.movier.exceptions.handler;

import org.movier.exceptions.interfaces.CustomHandledException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalUserExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String EmailIsInUseException (RuntimeException exception){
        if (exception instanceof CustomHandledException customException) {
            return customException.handleException();
        }
        return "Error accused: "+exception.getMessage();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(Model model) {
        model.addAttribute("message", "Sorry, the page you are looking for does not exist.");
        return "error";
    }

}

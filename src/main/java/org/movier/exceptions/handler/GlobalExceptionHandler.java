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
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomHandledException.class)
    @ResponseBody
    public String customRuntime (CustomHandledException exception){
        return exception.getCustomMessage();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(Model model) {
        model.addAttribute("message", "Sorry, the page you are looking for does not exist.");
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String runtime (RuntimeException exception){
        return exception.getMessage();
    }
}

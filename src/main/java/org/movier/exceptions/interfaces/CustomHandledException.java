package org.movier.exceptions.interfaces;

import org.springframework.http.ResponseEntity;

public interface CustomHandledException {
    ResponseEntity<String> handleException();
}

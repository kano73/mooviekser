package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserDontHaveRightsForActionException extends RuntimeException  implements CustomHandledException {

  @Override
  public ResponseEntity<String> handleException() {
    return new ResponseEntity<>("Email is already in owned by another user", HttpStatus.CONFLICT);
  }

  public UserDontHaveRightsForActionException(String message) {
        super(message);
    }
}

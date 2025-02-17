package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UserDontHaveRightsForActionException extends RuntimeException   implements CustomHandledException {

  @Override
  public String handleException() {
    return super.getMessage();
  }
  public UserDontHaveRightsForActionException(String message) {
        super(message);
    }
}

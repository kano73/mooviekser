package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class UserDontHaveRightsForActionException extends   CustomHandledException {
  public UserDontHaveRightsForActionException(String message) {
        super(message);
    }
}

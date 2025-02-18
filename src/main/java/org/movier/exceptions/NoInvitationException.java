package org.movier.exceptions;

import org.movier.exceptions.interfaces.CustomHandledException;

public class NoInvitationException extends CustomHandledException {
    public NoInvitationException(String message) {
        super(message);
    }
}

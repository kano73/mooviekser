package org.movier.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException{

        String error = "Incorrect password or username";
        if (exception instanceof DisabledException) {
            error = "Your email address is not active.";
        }
        if (exception instanceof LockedException) {
            error = "You were banned.";
        }

        response.sendRedirect("/login?error=" + error);
    }
}

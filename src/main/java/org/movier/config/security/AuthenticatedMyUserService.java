package org.movier.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.movier.exceptions.UserNotFoundException;
import org.movier.model.details.MyUserDetails;
import org.movier.model.entity.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedMyUserService {
    public MyUser getCurrentUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof MyUserDetails userDetails) {
            return userDetails.getMyUser();
        }else {
            throw new UserNotFoundException("Please login");
        }
    }

    public MyUserDetails getCurrentUserDetailsAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof MyUserDetails userDetails) {
            return userDetails;
        }else {
            throw new UserNotFoundException("Please login");
        }
    }

    public void logoutCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
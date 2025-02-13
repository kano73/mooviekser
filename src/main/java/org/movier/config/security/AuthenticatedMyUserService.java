package org.movier.config.security;


import org.movier.exceptions.UserNotFoundException;
import org.movier.model.details.MyUserDetails;
import org.movier.model.entity.MyUser;
import org.movier.repository.MyUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedMyUserService {
    public MyUser getCurrentUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof MyUserDetails userDetails) {
            return userDetails.getMyUser();
        }else {
            throw new UserNotFoundException("connected user not found");
        }
    }
}
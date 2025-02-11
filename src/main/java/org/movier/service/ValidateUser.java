package org.movier.service;

import org.movier.exceptions.EmailIsInUseException;
import org.movier.exceptions.UsernameIsInUseException;
import org.movier.model.entity.MyUser;
import org.movier.repository.MyUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidateUser {
    private final MyUserRepository myUserRepository;

    public ValidateUser(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    public void validateUser(MyUser user) {
        if (myUserRepository.findByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            throw new UsernameIsInUseException(user.getUsername()+" username is owned by another user");
        } else if (myUserRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new EmailIsInUseException(user.getEmail()+" email address is owned by another user");
        }
    }
}

package org.movier.service;

import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.repository.MyUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidationService emailValidationService;

    public MyUserService(MyUserRepository myUserRepository, UserValidator userValidator, PasswordEncoder passwordEncoder, EmailValidationService emailValidationService) {
        this.myUserRepository = myUserRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.emailValidationService = emailValidationService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class,  RuntimeException.class})
    public boolean registerUser(MyUser user) {
        userValidator.validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.USER);
        user.setEmailActivated(false);
        myUserRepository.save(user);
        try {
            emailValidationService.prepareEmailValidation(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Transactional
    public void verifyEmail(MyUser user) {
        myUserRepository.updateUserEmailActivatedById(user.getId(), true);
        emailValidationService.deleteByUser(user);
    }
}

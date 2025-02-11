package org.movier.service;

import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.repository.MyUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final ValidateUser validateUser;
    private final PasswordEncoder passwordEncoder;

    public MyUserService(MyUserRepository myUserRepository, ValidateUser validateUser, PasswordEncoder passwordEncoder) {
        this.myUserRepository = myUserRepository;
        this.validateUser = validateUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean registerUser(MyUser user) {
        validateUser.validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.USER);
        myUserRepository.save(user);
        return true;
    }
}
